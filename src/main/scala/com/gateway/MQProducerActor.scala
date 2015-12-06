package com.gateway


import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import com.gateway.MQProducerActor.ProducerConf
import com.thenewmotion.akka.rabbitmq.{BasicProperties, ChannelActor, ChannelMessage, CreateChannel}
import com.util.JsonSugar
import org.joda.time.DateTime
import org.json4s.native.Serialization.write

object MQProducerActor {

  case class Event(eventType: String, createdAt: DateTime, data: Map[String, Any])

  case class ProducerConf(name: String, exchangeType: String, durable: Boolean, routingKey: String, eventProperties: BasicProperties)

  def props(brokerManager: ActorRef, ex: ProducerConf) = Props(new MQProducerActor(brokerManager, ex))
}

class MQProducerActor(brokerManager: ActorRef, ex: ProducerConf) extends Actor with ActorLogging with JsonSugar{
  import com.gateway.MQProducerActor._
  import ex._

  brokerManager ! CreateChannel(
    ChannelActor.props({ (c, ref) => c.exchangeDeclare(name, exchangeType, durable) }),
    Some("publisher")

  )
  private val publisher = context.actorSelection("/user/event-stream/broker/publisher")

  override def receive: Receive = {
    case e@Event(eventType, createdAt, data) =>
      publisher ! ChannelMessage(
        _.basicPublish(name, routingKey, eventProperties, write(e).getBytes("UTF-8")),
        false
      )
  }
}

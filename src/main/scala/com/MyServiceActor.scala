package com

import akka.actor.{ActorLogging, Actor, ActorRef}
import com.configuration.AppConfiguration
import com.controller.EventController
import com.gateway.MQProducerActor
import com.thenewmotion.akka.rabbitmq.ConnectionActor

class MyServiceActor
  extends Actor
  with ActorLogging
  with EventController {

  import AppConfiguration._

  val brokerManager: ActorRef = actorRefFactory.actorOf(ConnectionActor.props(factory), "broker")

  val producer = actorRefFactory.actorOf(MQProducerActor.props(brokerManager, producerConf), "producer")

  def receive = runRoute(eventRoute)

  def actorRefFactory = context

}
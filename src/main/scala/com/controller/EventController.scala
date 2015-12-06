package com.controller

import akka.actor.ActorRef
import com.gateway.MQProducerActor.Event
import com.util.JsonSugar
import spray.routing.HttpService

trait EventController extends HttpService with JsonSugar{

  val producer: ActorRef

  val eventRoute =
    post {
      path("event") {
        entity(as[Event]) { e =>
          producer ! e
          complete {
            "OK"
          }
        }
      }
    }
}

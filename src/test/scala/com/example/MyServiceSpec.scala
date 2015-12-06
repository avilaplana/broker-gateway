package com.example

import akka.actor.ActorRef
import com.controller.EventController
import org.specs2.mutable.Specification
import spray.http.StatusCodes._
import spray.testkit.Specs2RouteTest
//
//class MyServiceSpec extends Specification with Specs2RouteTest with EventController {
//  def actorRefFactory = system
//
//  "MyService" should {
//
//    "return a greeting for GET requests to the root path" in {
//      Get() ~> myRoute ~> check {
//        responseAs[String] must contain("Say hello")
//      }
//    }
//
//    "leave GET requests to other paths unhandled" in {
//      Get("/kermit") ~> myRoute ~> check {
//        handled must beFalse
//      }
//    }
//
//    "return a MethodNotAllowed error for PUT requests to the root path" in {
//      Put() ~> sealRoute(myRoute) ~> check {
//        status === MethodNotAllowed
//        responseAs[String] === "HTTP method not allowed, supported methods: GET"
//      }
//    }
//  }
//  override val producer: ActorRef = ???
//}

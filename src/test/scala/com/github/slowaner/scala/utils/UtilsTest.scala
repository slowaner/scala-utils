package com.github.slowaner.scala.utils

import java.nio.charset.Charset

import org.scalatest.{FlatSpec, Matchers}
import  scala.collection.JavaConverters._

class UtilsTest extends FlatSpec with Matchers {
  "UtilsTest" should "get console CP wi native" in {
    val cp = OS.GetConsoleOutputCharset
    println(cp)
  }
}

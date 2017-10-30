package com.github.slowaner.scala.utils

import java.net.URI
import java.nio.charset.Charset

/**
  * Created by gunman on 14.04.2017.
  */
object Utilities {

  val Win1251_Charset: Charset = Charset.forName("windows-1251")
  val UTF8_Charset: Charset = Charset.forName("UTF-8")

  // convert from UTF-8 -> internal Java String format
  def ConvertFromUTF8(str: String, toCharset: Charset): String = {
    ConvertString(str, UTF8_Charset, toCharset)
  }

  // convert from internal Java String format -> UTF-8
  def ConvertToUTF8(str: String, fromCharset: Charset): String = {
    ConvertString(str, fromCharset, UTF8_Charset)
  }

  def ConvertString(str: String, fromCharset: Charset, toCharset: Charset): String = {
    if (str != null && fromCharset != null && toCharset != null) {
      new String(str.getBytes(toCharset), fromCharset)
    } else {
      null
    }
  }

  def splitUriQuery(uri: URI): Map[String, Seq[String]] = {
    if (uri == null) Map.empty[String, Seq[String]]
    else splitQuery(uri.getQuery)
  }

  def splitQuery(query: String): Map[String, Seq[String]] = {
    if (query == null || query.isEmpty) Map.empty[String, Seq[String]]
    else
      query.split("&").map(this.splitQueryParameter).groupBy(_._1).mapValues(_.map(_._2).toSeq)
  }

  def splitQueryParameter(it: String): (String, String) = {
    val splitted = it.split("=", 2)
    (splitted(0), splitted.applyOrElse(1, null))
  }
}

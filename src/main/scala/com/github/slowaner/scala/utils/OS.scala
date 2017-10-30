package com.github.slowaner.scala.utils

object OS {

  object OSTypes extends Enumeration {
    type OSType = Value
    val OSLinux: OSType = Value("OSLinux")
    val OSWindows: OSType = Value("OSWindows")
    val OSMacintosh: OSType = Value("OSMacintosh")
    val OSUnknown: OSType = Value("OSUnknown")
  }

  object OSArchs extends Enumeration {
    type OSArch = Value
    val Amd64: OSArch = Value("Amd64")
    val UnknownArch: OSArch = Value("UnknownArch")
  }

  val osType: OSTypes.OSType = {
    val os = sys.props("os.name").toLowerCase()
    if (os.startsWith("windows"))
      OSTypes.OSWindows
    else if (os.startsWith("linux"))
      OSTypes.OSLinux
    else if (os.startsWith("mac"))
      OSTypes.OSMacintosh
    else
      OSTypes.OSUnknown
  }

  val osVersion: String = sys.props("os.version")
  val osArch: OSArchs.OSArch = {
    val os = sys.props("os.arch").toLowerCase()
    if (os.startsWith("windows"))
      OSArchs.Amd64
    else
      OSArchs.UnknownArch
  }
  val osBits: Int = sys.props("sun.arch.data.model").toInt

  // Check OS name
  def isWindows: Boolean = {
    osType == OSTypes.OSWindows
  }

  def isMacintosh: Boolean = {
    osType == OSTypes.OSMacintosh
  }

  def isLinux: Boolean = {
    osType == OSTypes.OSLinux
  }

  // Check Arch
  def isAmd64: Boolean = {
    osArch == OSArchs.Amd64
  }

  // Check bits
  def isX64: Boolean = {
    osBits == 64
  }

  def isX86: Boolean = {
    osBits == 32
  }

  // Additional methods
  def getShortName: String = {
    if (osType == OSTypes.OSWindows) s"win$osBits"
    else s"unknown$osBits"
  }
}

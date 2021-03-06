package net.ceedubs.ficus
package readers

import com.typesafe.config.{Config, ConfigFactory}
import FicusConfig._

class ConfigReaderSpec extends Spec { def is = s2"""
  The Config value reader should
    read a config $readConfig
    implicitly read a config $implicitlyReadConfig
    read a ficus config $readFicusConfig
    implicitly read a ficus config $implicitlyReadFicusConfig
  """

  def readConfig = prop { i: Int =>
    val cfg = ConfigFactory.parseString(
      s"""
        |myConfig {
        |  myValue = $i
        |}
      """.stripMargin)
    configValueReader.read(cfg, "myConfig").getInt("myValue") must beEqualTo(i)
  }

  def implicitlyReadConfig = prop { i: Int =>
    val cfg = ConfigFactory.parseString(
      s"""
        |myConfig {
        |  myValue = $i
        |}
      """.stripMargin)
    cfg.as[Config]("myConfig").getInt("myValue") must beEqualTo(i)
  }

  def readFicusConfig = prop { i: Int =>
    val cfg = ConfigFactory.parseString(
      s"""
        |myConfig {
        |  myValue = $i
        |}
      """.stripMargin)
    ficusConfigValueReader.read(cfg, "myConfig").as[Int]("myValue") must beEqualTo(i)
  }

  def implicitlyReadFicusConfig = prop { i: Int =>
    val cfg = ConfigFactory.parseString(
      s"""
        |myConfig {
        |  myValue = $i
        |}
      """.stripMargin)
    cfg.as[FicusConfig]("myConfig").as[Int]("myValue") must beEqualTo(i)
  }
}

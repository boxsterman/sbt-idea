package org.sbtidea

/**
 * Copyright (C) 2010, Mikko Peltonen, Jon-Anders Teigen
 * Licensed under the new BSD License.
 * See the LICENSE file for details.
 */

import java.io.File
import sbt.Logger
import xml.Node
import java.nio.charset.UnmappableCharacterException

trait SaveableXml {
  val log: Logger
  def path: String
  def content: Node

  def save() {
    val file = new File(path)
    file.getParentFile.mkdirs()

    try {
      OutputUtil.saveFile(file, content)
      log.info("Created " + path)
    } catch {
      case e: UnmappableCharacterException => {
        val prettyPrint = new scala.xml.PrettyPrinter(150, 2)
        log.error("Unable to encode file: " + file)
        val text: String = prettyPrint.format(content)
        log.error("=> content pretty printed=" + text)
        log.error("=> List[Char]=" + text.toList)
      }
    }
  }
}
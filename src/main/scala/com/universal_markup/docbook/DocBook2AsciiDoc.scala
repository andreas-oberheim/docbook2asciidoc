package com.universal_markup.docbook

import java.io.File

import com.google.common.io.Resources

import scala.io.Source

object DocBook2AsciiDoc {

  val XSLT = new File(Resources.getResource("com/universal_markup/docbook/d2a_docbook.xsl").toURI)
}
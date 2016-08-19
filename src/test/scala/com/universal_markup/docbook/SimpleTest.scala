package com.universal_markup.docbook

import java.io.{StringReader, StringWriter}
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.{StreamResult, StreamSource}

import org.xml.sax.InputSource

import scala.xml.Utility

object SimpleTest extends App {
  val xml =
    """<?xml version="1.0"?>
      |<!DOCTYPE article PUBLIC "-//OASIS//DTD DocBook XML V4.5//EN"
      |         "http://www.oasis-open.org/docbook/xml/4.5/docbookx.dtd">
      |<article>
      |  <title>Docbook Article Example</title>
      |
      |  <articleinfo>
      |    <title>DocBook Intro</title>
      |    <author>
      |      <firstname>Lars</firstname>
      |      <surname>Vogel</surname>
      |    </author>
      |    <volumenum>1234</volumenum>
      |  </articleinfo>
      |
      |  <chapter>
      |    <title>This is the first chapter</title>
      |    <section>
      |      <title>First section in the chapter</title>
      |      <para>Test</para>
      |      <section>
      |        <title>First sub section</title>
      |        <para>Subsection1</para>
      |      </section>
      |      <section>
      |        <title>second sub section</title>
      |        <para> Subsection2</para>
      |      </section>
      |    </section>
      |    <section>
      |      <title>Second section in the chapter</title>
      |      <para>Other random text</para>
      |      <para>
      |        <mediaobject>
      |          <imageobject>
      |            <imagedata fileref="images/title.png"/>
      |          </imageobject>
      |          <textobject>
      |            <phrase>Image description</phrase>
      |          </textobject>
      |      </mediaobject>
      |      </para>
      |    </section>
      |  </chapter>
      |
      |  <chapter>
      |    <title>This is the second chapter</title>
      |    <section>
      |      <title>My Title</title>
      |      <para>More...</para>
      |    </section>
      |    <section>
      |      <title>Other title</title>
      |      <para>Blabla</para>
      |    </section>
      |  </chapter>
      |</article>""".stripMargin

  val xmlSource = new InputSource(new StringReader(xml))
  val factory = DocumentBuilderFactory.newInstance()
  factory.setNamespaceAware(true)
  factory.setIgnoringElementContentWhitespace(true)
  val builder = factory.newDocumentBuilder()
  val document = builder.parse(xmlSource)

  // Use a Transformer for output
  val tFactory = new net.sf.saxon.TransformerFactoryImpl()


  val stylesource = new StreamSource(com.universal_markup.docbook.DocBook2AsciiDoc.XSLT)
  val transformer = tFactory.newTransformer(stylesource)

  val writer = new StringWriter()

  val source = new DOMSource(document)
  val result = new StreamResult(writer)
  transformer.transform(source, result)
  val converted = writer.toString()

  println(converted)
}
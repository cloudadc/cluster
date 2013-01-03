package com.kylin.infinispan.datagrid.helloworld;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.Cache;

/**
 * A simple servlet requesting the cache for key "hello".
 */
@SuppressWarnings("serial")
@WebServlet("/TestServletGet")
public class TestServletGet extends HttpServlet {

   private static final String PAGE_HEADER = "<html><head /><body>";

   private static final String PAGE_FOOTER = "</body></html>";

   @Inject
   private Logger log;

   @Inject
   DefaultCacheManager m;

   @Override
   protected void doGet(HttpServletRequest req, HttpServletResponse resp)
         throws ServletException, IOException {

      log.info("putting hello");
      Cache<String, String> c = m.getCache();
      String x = (String) c.get("hello");

      PrintWriter writer = resp.getWriter();
      writer.println(PAGE_HEADER);
      writer.println("<h1>" + "Get Infinispan: " + x + "</h1>");
      writer.println(PAGE_FOOTER);
      writer.close();
   }

}

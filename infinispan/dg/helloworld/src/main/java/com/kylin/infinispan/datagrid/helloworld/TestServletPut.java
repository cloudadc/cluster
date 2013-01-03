package com.kylin.infinispan.datagrid.helloworld;

import java.io.IOException;
import java.io.PrintWriter;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.Cache;
import java.util.logging.Logger;

/**
 * A simple servlet storing value for key "hello" into the cache.
 */
@SuppressWarnings("serial")
@WebServlet("/TestServletPut")
public class TestServletPut extends HttpServlet {

   private static final String PAGE_HEADER = "<html><head /><body>";

   private static final String PAGE_FOOTER = "</body></html>";

   @Inject
   private Logger log;

   @Inject
   DefaultCacheManager m;

   @Override
   protected void doGet(HttpServletRequest req, HttpServletResponse resp)
         throws ServletException, IOException {

      log.info("putting hello-world");
      Cache<String, String> c = m.getCache();
      c.put("hello", "world");

      PrintWriter writer = resp.getWriter();
      writer.println(PAGE_HEADER);
      writer.println("<h1>" + "Put Infinispan: " + c.get("hello") + "</h1>");
      writer.println(PAGE_FOOTER);
      writer.close();
   }

}

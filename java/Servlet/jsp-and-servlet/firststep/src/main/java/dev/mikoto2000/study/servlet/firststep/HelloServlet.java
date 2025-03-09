package dev.mikoto2000.study.servlet.firststep;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/hello")
public class HelloServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
      response.setContentType("text/html;charset=UTF-8");
      ServletContext context = this.getServletContext();
      RequestDispatcher dispatcher = context.getRequestDispatcher("/hello.jsp");
      dispatcher.forward(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
      response.setContentType("text/html;charset=UTF-8");
      String str = request.getParameter("name");
      request.setAttribute("name", str);

      ServletContext context = this.getServletContext();
      RequestDispatcher dispatcher = context.getRequestDispatcher("/hello.jsp");
      dispatcher.forward(request, response);
  }
}


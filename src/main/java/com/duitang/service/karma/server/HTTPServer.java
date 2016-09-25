package com.duitang.service.karma.server;

import com.duitang.service.karma.KarmaException;
import com.duitang.service.karma.router.JsonRouter;
import com.duitang.service.karma.router.Router;
import com.duitang.service.karma.transport.JsonServlet;

public class HTTPServer implements RPCService {

  final static int DEFAULT_PORT = 7777;

  protected int port;
  protected org.eclipse.jetty.server.Server server;
  protected JsonServlet servlet = new JsonServlet();

  public HTTPServer() {
    this(DEFAULT_PORT);
  }

  public HTTPServer(int port) {
    this.port = port;
  }

  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }

  @Override
  public void setRouter(Router router) {
    this.servlet.setRouter((JsonRouter) router);
  }

  @Override
  public void start() throws KarmaException {
    try {
      this.server = new org.eclipse.jetty.server.Server(this.port);
      this.server.setHandler(servlet);
      this.server.start();
    } catch (Exception e) {
      throw new KarmaException(e);
    }
  }

  @Override
  public void stop() {
    try {
      server.stop();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}

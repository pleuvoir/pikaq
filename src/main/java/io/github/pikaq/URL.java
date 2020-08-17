package io.github.pikaq;


public class URL {

  public URL() {
  }

  public URL(String host, int port) {
    this.host = host;
    this.port = port;
  }

  private String host;

  private int port;

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public String toAddress() {
    return this.getHost() + ":" + this.getPort();
  }
}

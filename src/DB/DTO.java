package DB;

import java.net.Socket;

public class DTO
{
	private String id = "";
	private String pwd = "";
	private Socket clientSocket = null;
	
	
	public DTO(Socket client)
	{
		this.clientSocket = client;
	}
	
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getPwd()
	{
		return pwd;
	}
	public void setPwd(String pwd)
	{
		this.pwd = pwd;
	}
	
	public Socket getClientSocket()
	{
		return clientSocket;
	}
	
}

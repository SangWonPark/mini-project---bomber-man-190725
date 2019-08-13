package Util;

import java.util.StringTokenizer;

import DB.DAO;
import DB.DTO;
import Main.GreenRoom;
import Main.Room;
import Main.WithClient;

public class Util
{
	
	DAO dao = null;
	GreenRoom gr = null;
	
	
	public Util()
	{
		dao = DAO.getInstance();
		gr = GreenRoom.getGreenroom();
	}
	
	public boolean accChk(String accInfo, DTO dto) {
		
		StringTokenizer tokens = new StringTokenizer(accInfo);
		
		String nextstep = tokens.nextToken();
		
		dto.setId(tokens.nextToken());
		dto.setPwd(tokens.nextToken());
		
		DTO getDto = null;
		int chkAdd = 0;
		if (nextstep.equals("/add"))
		{
			chkAdd = dao.insert(dto);
		} 
		else if (nextstep.equals("/login"))
		{
			getDto = dao.selAcc(dto);
		}
		if (getDto!=null | chkAdd!=0)
		{
			return true;
		}
		return false;
	}
	
	public Room selRoom(String str, WithClient dto) {
		
		StringTokenizer tokens = new StringTokenizer(str);
		
		int roomNum = Integer.valueOf(tokens.nextToken());
		System.out.println(roomNum);
		return gr.joinRoom(roomNum, dto);
		
	}
}

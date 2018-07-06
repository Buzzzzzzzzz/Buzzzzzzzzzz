import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.swetake.util.Qrcode;


public class Hh {
	public static void main(String args[]) throws Exception{
		int version=16;//4*15+21
		int width=263;
		int height=263;
		Qrcode qrcode=new Qrcode();
		String content="BEGIN:VCARD\n"+
		"N:郝\n"+
		"FN:优秀\n"+
		"ORG:河北科技师范学院\n"+ 
		"TITLE:小学生\n"+
		"BDAY:1998-3-30\n"+
		"ADR;WORK:秦皇岛市海港区河北大街西段360号\n"+
		"ADR;HOME:你家旁边\n"+
		"TEL;CELL,VOICE:15028580902\n"+
		"TEL;WORK,VOICE:暂时没有，不够级别\n"+
		"URL;WORK;:http://www.icbc.com.cn\n"+
		"EMAIL;INTERNET,HOME:1045728573@qq.com\n"+
		"END:VCARD";
		qrcode.setQrcodeVersion(version);
		byte []data=content.getBytes("utf-8");
		boolean[][] qrdata=qrcode.calQrcode(data);
		//设置图片缓冲区
		BufferedImage bufferedImage=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		//图片绘画
		Graphics2D gs=bufferedImage.createGraphics();
		//二维码居中
//		int a=bufferedImage.getWidth();
//		int b=bufferedImage.getHeight();
		//设置背景色
		gs.setBackground(Color.WHITE);
		//gs.setColor(Color.BLACK);
		//清除画布
		gs.clearRect(0, 0, width, height);
		//二维码绘画
		for(int i=0;i<qrdata.length;i++)
			for(int j=0;j<qrdata.length;j++){
				if(qrdata[i][j]){
					int startR=26;int startG=253;int startB=253;
					int endR=241;int endG=37; int endB=241;
					int num1=startR+(endR-startR)*((i+j)/2)/qrdata.length;
					int num2=startG+(endG-startG)*((i+j)/2)/qrdata.length;
					int num3=startB+(endB-startB)*((i+j)/2)/qrdata.length;
					Color color=new Color(num1,num2,num3);
					gs.setColor(color);
					gs.fillRect(i*3+10,j*3+10,3,3);/*fillRect(int x, int y, int width, int height)
					i,j为指定矩形的长和宽，width为填充的宽和i等比，height相同*/
				}
			}
		BufferedImage logo=scale("D:/logo2.png",80,80,true);
//		int logoSize=(qrdata.length)*10/4;
//		int o=((qrdata.length)*10-logoSize)/2;
		int location=(width-logo.getHeight())/2;
		gs.drawImage(logo, location, location, 80, 80,null);
		gs.dispose();//关闭绘图
		bufferedImage.flush();
		try{
			ImageIO.write(bufferedImage,"png",new File("D:/qrcode.png"));
		}catch(IOException e){
			e.printStackTrace();
			System.out.println("产生了问题");
		}
		System.out.println("生成中。。");
		
	}
		
		private static BufferedImage scale(String logoPath,int width,int height,boolean hasFiller)throws Exception{
			double ratio=0.0;//缩放比例
			File file=new File(logoPath);
			BufferedImage srcImage=ImageIO.read(file);
			Image destImage =srcImage.getScaledInstance(width,height,BufferedImage.SCALE_SMOOTH);
			//计算比例
			if((srcImage.getHeight()>height)||(srcImage.getWidth()>width)){}
			if(srcImage.getHeight()>srcImage.getWidth()){
				ratio=(new Integer(height)).doubleValue()/srcImage.getHeight();
			}else{
				ratio =(new Integer(width)).doubleValue()/srcImage.getWidth();
			}
			AffineTransformOp op=new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);
			destImage =op.filter(srcImage,null);
			
			//补白
			if(hasFiller){
				BufferedImage image=new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB );
				Graphics2D graphic =image.createGraphics();
				graphic.setColor(Color.white);
				graphic.fillRect(0, 0, width, height);
				if(width==destImage.getWidth(null)){
					graphic.drawImage(destImage, 0, (height-destImage.getHeight(null))/2,destImage.getWidth(null),
					destImage.getHeight(null),Color.white,null);
				}else{
					graphic.drawImage(destImage, 0, (width-destImage.getWidth(null))/2,destImage.getWidth(null),
					destImage.getHeight(null),Color.white,null);
				}
				graphic.dispose();
				destImage=image;
			}
			
			return (BufferedImage) destImage;
	}

}

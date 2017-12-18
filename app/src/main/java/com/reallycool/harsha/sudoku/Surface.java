package com.reallycool.harsha.sudoku;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

/**
 * Created by harsha on 6/12/17.
 */

class Surface extends SurfaceView implements Runnable, SurfaceHolder.Callback, View.OnTouchListener {
    Thread t;
SurfaceHolder sh;
Bitmap smile;
    float sx=0,sy=0;
    float w;
    boolean touch = false;
    float col;
    int temp=-1;
    float y=-1,x=-1;
    float  ty=-1,tx=-1;
    Color back;
    int c=-1, r=-1;
    int [][]a=new int[9][9];
static boolean running=true;
Context con;
Paint p;

    int b[]={1,2,3,4,5,6,7,8,9};
    int xx1[]={-1,-1,-1};
    int yy1[]={-1,-1,-1};
    int count=0;
int width=0;
    public Surface(Context context) {
        super(context);
        t = new Thread(this);
        smile = BitmapFactory.decodeResource(getResources(), R.drawable.smile);
        sh=getHolder();
        p=new Paint();
        p.setStrokeWidth(3);
        con=context;
        sh.addCallback(this);
        setOnTouchListener(this);
        p.setTextSize(40*getWidth()/480);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        t.start();

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
width=getWidth();
        col=width/11;
        w=col*10;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
running = false;
        a=new int[9][9];
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (running) {
            Canvas can = sh.lockCanvas();
            can.drawColor(Color.WHITE);
            p.setColor(Color.argb(200,200,200,200));
            can.drawRect(0, 0, this.getWidth(), getWidth(), p);
            p.setColor(Color.BLACK);
            for(int i=1;i<11;i++){
                if((i-1)%3==0)p.setStrokeWidth(4);
                else p.setStrokeWidth(3);
            can.drawLine(col*i,col,col*i,w,p);}

            for(int i=1;i<11;i++){
                if((i-1)%3==0)p.setStrokeWidth(4);
                else p.setStrokeWidth(3);
                can.drawLine(col,col*i,w,col*i,p);}
            if(touch){
                p.setColor(Color.CYAN);
                can.drawRect(c*col,r*col,(c+1)*col,(r+1)*col,p);
                p.setColor(Color.BLACK);
                if(temp>0)
                can.drawText(temp+" ",c*col+col/4,r*col+col,p);
            }
            for(int i=0;i<9;i++) {
            for(int j=0;j<9;j++) {
                    if(a[i][j]>0)
                    can.drawText(a[i][j]+"",(i+1)*col+col/4,(j+2)*col,p);
            }
            }
            can.drawBitmap(smile,getWidth()/2-smile.getWidth()/2,(3*getHeight())/4,p);
            sh.unlockCanvasAndPost(can);
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {

        if(event.getAction()== MotionEvent.ACTION_DOWN){
            sx=event.getX();sy=event.getY();
            if(event.getX()<col||event.getY()<col)return true;
              x=event.getX();   y=event.getY();
              temp=0;
            c=(int)(x/col);r=(int)(y/col);
        if(c<10&&r<10&&c>0&r>0)touch=true;}

        if(event.getAction()== MotionEvent.ACTION_MOVE){
            ty=event.getY();tx=event.getX();
             temp=(int)(ty/col)-(int)(y/col)+Math.abs((int)(tx/col)-(int)(x/col));
             if(temp>9)temp=9;
         }
        if(event.getAction()== MotionEvent.ACTION_UP) {
            if((sx>getWidth()/2-smile.getWidth()/2&&sx<getWidth()/2+smile.getWidth()/2&&sy>(3*getHeight())/4&&sy<(3*getHeight())/4+smile.getHeight())&&event.getY()>(3*getHeight())/4&&event.getY()<(3*getHeight())/4+smile.getHeight()){
            long time=System.currentTimeMillis();
                algorithm();
                Log.e((System.currentTimeMillis() - time)+" ", "Time");
            return true;
        }
            if(temp<10&&touch){
                if(a[(int)(x/col)-1][(int)(y/col)-1]==temp){touch=false;return true;}
                if(temp>0){
                for(int i=0;i<9;i++){
                    if(a[i][(int)(y/col)-1]==temp){
                        Toast.makeText(con, temp+" already exists in the row", Toast.LENGTH_SHORT).show();
                        touch=false;return true;
                    }
                    if(a[(int)(x/col)-1][i]==temp){
                        Toast.makeText(con, temp+" already exists in the column", Toast.LENGTH_SHORT).show();
                        touch=false;return true;
                    }
                }
                    int xx=(int)(x/col)-1,yy=(int)(y/col)-1;
                    xx/=3;yy/=3;
                    for(int k=xx*3;k<xx*3+3;k++){
                        for(int l=yy*3;l<yy*3+3;l++){
                            if(a[k][l]==temp){
                                Toast.makeText(con, temp+" already exists in the Square", Toast.LENGTH_SHORT).show();
                                touch=false;return true;
                            }
                        }
                    }


                }
            a[(int)(x/col)-1][(int)(y/col)-1]=temp;
            }


        touch=false;
        }
        return true;
    }




    //algorithm
      void algorithm(){
    while(true){
        int an=0;
        //detect unique number
        for(int k=0;k<9;k++){
            for(int l=0;l<9;l++){
                if(a[k][l]>0)continue;
                int bb[]=b.clone();
                for(int i=0;i<9;i++){
                    if(a[k][i]>0)
                        bb[a[k][i]-1]=-1;
                    if(a[i][l]>0)
                        bb[a[i][l]-1]=-1;
                }
                int kk=k/3;
                kk*=3;
                int ll=l/3;
                ll*=3;
                for(int i=kk;i<kk+3;i++){
                    for(int j=ll;j<ll+3;j++){
                        if(a[i][j]>0)
                            bb[a[i][j]-1]=-1;
                    }
                }
                int c=0,t=0;
                for(int i=0;i<9;i++){
                    if(bb[i]>0){c++;t=bb[i];}
                }
                if(c==1){
                    count++;
                    a[k][l]=t; //assign
                    an++;
                }
            }
        }//end unique number

        //For all the 9 different numbers
        for(int n=1;n<10;n++){

            boolean boo[][]=new boolean[9][9];

            //For every slot

            for(int i=0;i<9;i++){
                for(int j=0;j<9;j++){
                    if(a[i][j]==n){
                        fill(i,j,boo);
                    }
                    else if(a[i][j]>0)boo[i][j]=true;
                }
            }
            //checking
            f:	for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){
                    //for each square area
                    int c=0;int x1[]=xx1.clone(),y1[]=yy1.clone();
                    for(int k=i*3;k<i*3+3;k++){
                        for(int l=j*3;l<j*3+3;l++){
                            if(boo[k][l]==false){
                                c++;
                                if(c<4){
                                    x1[c-1]=k;y1[c-1]=l;
                                }
                            }
                        }
                    }
                    if(x1[0]>-1&&x1[1]==-1){
                        a[x1[0]][y1[0]]=n;//assign
                        count++;
                        an++;
                        fill(x1[0],y1[0],boo);
                    }
                    //advanced detection
                    if(c<4&&c>1){
                        if(x1[0]==x1[1]&&(x1[1]==x1[2]||x1[2]==-1)){
                            boolean is=false;
                            for(int d=0;d<9;d++){
                                if(d>=j*3&&d<j*3+3)continue;
                                if(boo[x1[0]][d]==false)is=true;
                                boo[x1[0]][d]=true;
                            }
                            if(is){i=0; continue f;}
                        }
                        if(y1[0]==y1[1]&&(y1[1]==y1[2]||y1[2]==-1)){
                            boolean is=false;
                            for(int d=0;d<9;d++){
                                if(d>=i*3&&d<i*3+3)continue;
                                if(boo[d][y1[0]]==false)is=true;
                                boo[d][y1[0]]=true;
                            }
                            if(is){i=0;continue f;}
                        }
                    }//adv
                }}
            boolean bool=false;
            for(int i=0;i<9;i++){
                int d=0,tem=0;
                for(int j=0;j<9;j++){
                    if(boo[i][j]==false){d++;tem=j;}
                }
                if(d==1){a[i][tem]=n;//assign
                    fill(i,tem,boo);
                    count++;an++;bool=true;}
            }

            for(int i=0;i<9;i++){
                int d=0,tem=0;
                for(int j=0;j<9;j++){
                    if(boo[j][i]==false){d++;tem=j;}
                }
                if(d==1){a[tem][i]=n;//assign
                    fill(tem,i,boo);
                    count++;an++;}
            }

        }

        if(an==0)break;
    }}
      void fill(int a,int b, boolean boo[][]){

        for(int k=0;k<9;k++){
            boo[a][k]=true;
            boo[k][b]=true;
        }
        int xx=a/3,y=b/3;
        for(int k=xx*3;k<xx*3+3;k++){
            for(int l=y*3;l<y*3+3;l++){
                boo[k][l]=true;
            }
        }
    }
}


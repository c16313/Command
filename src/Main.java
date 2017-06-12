/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import command.*;
import drawer.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Main extends JFrame implements ActionListener{
/*public class Main extends JFrame implements ActionListener, MouseMotionListener,
        WindowListener {*/
    // 描画履歴
    private MacroCommand history = new MacroCommand();
    // 描画領域
    private DrawCanvas canvas = new DrawCanvas(400, 400, history);
    // 消去ボタン
    private JButton clearButton = new JButton("clear");
    // 赤ボタン
    //private JButton redButton = new JButton("red");
    // 緑ボタン
    //private JButton greenButton = new JButton("green");
    // 青ボタン
    //private JButton blueButton = new JButton("blue");
    //undoボタン
    private JButton undoButton = new JButton("undo");
    
    // コンストラクタ
    public Main(String title){
        super(title);
        
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e){
                System.exit(0);
            }
        });
        canvas.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e){
                Command cmd = new DrawCommand(canvas, e.getPoint());
                history.append(cmd);
                cmd.execute();
            }
        });
        clearButton.addActionListener(this);
        //redButton.addActionListener(this);
        //greenButton.addActionListener(this);
        //blueButton.addActionListener(this);
        undoButton.addActionListener(this);
        
        Box buttonBox = new Box(BoxLayout.X_AXIS);
        buttonBox.add(clearButton);
        //buttonBox.add(redButton);
        //buttonBox.add(greenButton);
        //buttonBox.add(blueButton);
        buttonBox.add(undoButton);
        Box mainBox = new Box(BoxLayout.Y_AXIS);
        mainBox.add(buttonBox);
        mainBox.add(canvas);
        getContentPane().add(mainBox);
        
        pack();
        show();
    }
    
    // ActionListener用
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == clearButton){
            history.clear();
            canvas.init();
            canvas.repaint();
        }/*else if(e.getSource() == redButton){
            Command cmd = new ColorCommand(canvas, Color.red);
            history.append(cmd);
            cmd.execute();
        }else if(e.getSource() == greenButton){
            Command cmd = new ColorCommand(canvas, Color.green);
            history.append(cmd);
            cmd.execute();
        }else if(e.getSource() == blueButton){
            Command cmd = new ColorCommand(canvas, Color.blue);
            history.append(cmd);
            cmd.execute();
        }*/
        else if(e.getSource() == undoButton){
            history.undo();
            canvas.repaint();
        }
    }
    
    // MouseMotionListener用
    public void mouseMoved(MouseEvent e){
        
    }
    public void mouseDragged(MouseEvent e){
        Command cmd = new DrawCommand(canvas, e.getPoint());
        
        history.append(cmd);
        cmd.execute();
    }
    
    // WindowListener用
    public void windowClosing(WindowEvent e){
        System.exit(0);
    }
    
    public void windowActivated(WindowEvent e){}
    public void windowClosed(WindowEvent e){}
    public void windowDeactivated(WindowEvent e){}
    public void windowDeiconified(WindowEvent e){}
    public void windowIconified(WindowEvent e){}
    public void windowOpened(WindowEvent e){}
    
    public static void main(String[] args){
        new Main("Command Pattern Sample");
    }
}

//************************************//
//** Android Calculator V0.1		**//
//** Author: Wang Tao				**//
//** Blog: http://wangtao.cublog.cn **//
//** Date: 2008-1-30				**//
//************************************//
package edu.bupt.hutaow;

import android.app.Activity;
import android.os.Bundle;
import android.widget.*;
import android.view.View.*;
import android.view.*;

public class Calculator extends Activity
{
	private Button[] btn = new Button[10];
	private EditText et_led;
	private TextView tv_mem;
	private Button
		btn_div, btn_mul, btn_sub, btn_plus, btn_equal,
		btn_bksp, btn_ce, btn_c, btn_dot, btn_xm, btn_rm, btn_mc,
		btn_pm;

	public double predata = 0;
	public String preopt = "=";
	public boolean vbegin = true;

    public void onCreate(Bundle icicle)
    {
        super.onCreate(icicle);
        setContentView(R.layout.main);

		et_led = (EditText)findViewById(R.id.et_led);
		tv_mem = (TextView)findViewById(R.id.tv_mem);
		btn[0] = (Button)findViewById(R.id.btn_0);
 		btn[1] = (Button)findViewById(R.id.btn_1);
 		btn[2] = (Button)findViewById(R.id.btn_2);
 		btn[3] = (Button)findViewById(R.id.btn_3);
 		btn[4] = (Button)findViewById(R.id.btn_4);
 		btn[5] = (Button)findViewById(R.id.btn_5);
 		btn[6] = (Button)findViewById(R.id.btn_6);
 		btn[7] = (Button)findViewById(R.id.btn_7);
		btn[8] = (Button)findViewById(R.id.btn_8);
		btn[9] = (Button)findViewById(R.id.btn_9);
		btn_div = (Button)findViewById(R.id.btn_div);
		btn_mul = (Button)findViewById(R.id.btn_mul);
		btn_sub = (Button)findViewById(R.id.btn_sub);
		btn_plus = (Button)findViewById(R.id.btn_plus);
		btn_equal = (Button)findViewById(R.id.btn_equal);
		btn_bksp = (Button)findViewById(R.id.btn_bksp);
		btn_ce = (Button)findViewById(R.id.btn_ce);
		btn_c = (Button)findViewById(R.id.btn_c);
		btn_dot = (Button)findViewById(R.id.btn_dot);
		btn_xm = (Button)findViewById(R.id.btn_xm);
		btn_rm = (Button)findViewById(R.id.btn_rm);
		btn_mc = (Button)findViewById(R.id.btn_mc);
		btn_pm = (Button)findViewById(R.id.btn_pm);

		for(int i = 0; i < 10; ++i) {
			btn[i].setOnClickListener(actionPerformed);
		}
		btn_div.setOnClickListener(actionPerformed);
		btn_mul.setOnClickListener(actionPerformed);
		btn_sub.setOnClickListener(actionPerformed);
		btn_plus.setOnClickListener(actionPerformed);
		btn_equal.setOnClickListener(actionPerformed);
		btn_bksp.setOnClickListener(actionPerformed);
		btn_ce.setOnClickListener(actionPerformed);
		btn_c.setOnClickListener(actionPerformed);
		btn_dot.setOnClickListener(actionPerformed);
		btn_xm.setOnClickListener(actionPerformed);
		btn_rm.setOnClickListener(actionPerformed);
		btn_mc.setOnClickListener(actionPerformed);
		btn_pm.setOnClickListener(actionPerformed);
		
    }
	
	private OnClickListener actionPerformed = new OnClickListener() {
		public void onClick(View v) {
			String
				command = ((Button)v).getText().toString(),
				str = et_led.getText().toString();
			
			if(command.compareTo("Bksp") == 0) {
				if(str.length() > 1)
					et_led.setText(str.substring(0, str.length() - 1));
				else if(str.length() == 1) {
					et_led.setText("0");
					vbegin = true;
				}
				if(et_led.getText().toString().compareTo("-") == 0) {
					et_led.setText("0");
					vbegin = true;
				}
			} else if(command.compareTo("CE") == 0) {
				et_led.setText("0");
				vbegin = true;
			} else if(command.compareTo("C") == 0) {
				et_led.setText("0");
				vbegin = true;
				predata = 0;
				preopt = "=";
			} else if(command.compareTo("X-M") == 0) {
				tv_mem.setText(str);
			} else if(command.compareTo("RM") == 0) {
				if(tv_mem.getText().toString().compareTo("0") != 0) {
					et_led.setText(tv_mem.getText());
					vbegin = false;
				}
			} else if(command.compareTo("MC") == 0) {
				tv_mem.setText("0");
			} else if(command.compareTo(".") == 0) {
				boolean nodot = (str.indexOf('.') == -1);
				if(nodot) {
					et_led.append(".");
				}
			} else if(command.compareTo("+/-") == 0) {
				try{
					double data = Double.parseDouble(str);
					data = data * (-1);
					et_led.setText(String.valueOf(data));
				} catch(NumberFormatException err) {
					et_led.setText("Number Format ERROR!");
				}
			} else if("0123456789".indexOf(command) != -1) {
				wtNumber(command);
			} else if("+-*/=".indexOf(command) != -1) {
				wtOperater(command);
			}
		}
	};

	private void wtNumber(String str) {
		if(vbegin)
			et_led.setText(str);
		else
			et_led.append(str);

		vbegin = false;
	}

	private void wtOperater(String opt) {
		try {
			double temp = Double.parseDouble(et_led.getText().toString());
			
			if(vbegin)
				preopt = opt;
			else {
				if(preopt.equals("=")) {
					predata = temp;
				} else if(preopt.equals("+")) {
					predata += temp;
				} else if(preopt.equals("-")) {
					predata -= temp;
				} else if(preopt.equals("*")) {
					predata *= temp;
				} else if(preopt.equals("/")) {
					if(temp != 0) {
						predata /= temp;
					} else {
						throw new ArithmeticException();
					}
				}
				et_led.setText(String.valueOf(predata));
				preopt = opt;
			}
		} catch(NumberFormatException e) {
			et_led.setText("Number Format ERROR!");
		} catch(ArithmeticException e) {
			et_led.setText("Div Number CAN NOT a ZERO!");
			preopt = "=";
		} finally {
			vbegin = true;
		}
	}
}

package pattern_study.Mediator;

import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends Frame implements Mediator, ActionListener {
	
	private ColleagueCheckbox checkGuest;
	private ColleagueCheckbox checkLogin;
	private ColleagueTextField textUser;
	private ColleagueTextField textPass;
	private ColleagueButton buttonOk;
	private ColleagueButton buttonCancel;
	
	
	public LoginFrame(String title) {
		super(title);
		
		// 배경색 설정
		setBackground(Color.lightGray);
		// 레이아웃 설정
		setLayout(new GridLayout(4, 2));
		
		// colleague들 생성
		createColleagues();
		
		// 배치
		add(checkGuest);
		add(checkLogin);
		add(new Label("Username : "));
		add(textUser);
		add(new Label("Password : "));
		add(textPass);
		add(buttonOk);
		add(buttonCancel);
		
		// 유효/무효의 초기 지정
		colleagueChanged();
		
		// 표시
		pack();
		show();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(e.toString());
		System.exit(0);
	}

	@Override
	public void createColleagues() {
		// 생성
		CheckboxGroup g = new CheckboxGroup();
		checkGuest = new ColleagueCheckbox("Guest", g, true);
		checkLogin = new ColleagueCheckbox("Login", g, false);
		textUser = new ColleagueTextField("", 10);
		textPass = new ColleagueTextField("", 10);
		textPass.setEchoChar('*');
		buttonOk = new ColleagueButton("OK");
		buttonCancel = new ColleagueButton("Cancel");
		// Mediator 세팅
		checkGuest.setMediator(this);
		checkLogin.setMediator(this);
		textUser.setMediator(this);
		textPass.setMediator(this);
		buttonOk.setMediator(this);
		buttonCancel.setMediator(this);
		// Listener 세팅
		checkGuest.addItemListener(checkGuest);
		checkLogin.addItemListener(checkLogin);
		textUser.addTextListener(textUser);
		textPass.addTextListener(textPass);
		buttonOk.addActionListener(this);
		buttonCancel.addActionListener(this);
	}

	@Override
	public void colleagueChanged() {
		if(checkGuest.getState()) {
			textUser.setColleagueEnabled(false);
			textPass.setColleagueEnabled(false);
			buttonOk.setColleagueEnabled(true);
		}
		else {
			textUser.setColleagueEnabled(true);
			userpassChanged();
		}
	}
	
	private void userpassChanged() {
		if(textUser.getText().length() > 0) {
			textPass.setColleagueEnabled(true);
			
			if(textPass.getText().length() > 0) {
				buttonOk.setColleagueEnabled(true);
			}
			else {
				buttonOk.setColleagueEnabled(false);
			}
		}
		else {
			textPass.setColleagueEnabled(false);
			buttonOk.setColleagueEnabled(false);
		}
	}
}

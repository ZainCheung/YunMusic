package com.robot;

import java.util.ArrayList;
import java.util.List;

public class Database {

	List<String> answers = new ArrayList<String>();//��ضȳ�ȡ��
	List<String> unknownAnswers = new ArrayList<String>();//�޷�����
	List<String> oneCharAnswers = new ArrayList<String>();//һ���ַ����ؿ�
	List<String> questionAnswers = new ArrayList<String>();
	
	List<String> givenList_Hi = new ArrayList<String>();//���к����տ�
	List<String> answersList_Hi = new ArrayList<String>();//���к����ؿ�
	
	List<String> givenList_KuaKua = new ArrayList<String>();//�����տ�
	List<String> answersList_KuaKua = new ArrayList<String>();//��䷵�ؿ�
	
	List<String> givenTranslate = new ArrayList<String>();//�������ݽ��տ�
	List<String> answersTranslate = new ArrayList<String>();//���뷴����ʾ��
	List<String> errorTranslate = new ArrayList<String>();//�������ݽ��տ�


	public List<String> getAnswers() {
		return answers;
	}

	public void setAnswers(String string) {
		this.answers.add(string);
	}

	public List<String> getQuestionAnswers() {
		return unknownAnswers;
	}

	public void setQuestionAnswers(String string) {
		this.unknownAnswers.add(string);
	}
	
	public List<String> getUnknownAnswers() {
		return unknownAnswers;
	}

	public void setUnknownAnswers(String string) {
		this.unknownAnswers.add(string);
	}

	public Database() {
		setAnswers("����");
		setAnswers("ŶŶ");
		setAnswers("������������,Ӧ���Ǹ�����!");
		setAnswers("��ѽ��ѽ�����Ҹ����ϣ�");
		setAnswers("���ڼ���������֪��!");
		setAnswers("С��������!");
		setAnswers("����");
		setAnswers("������ϱ��ȥ����!");
		setAnswers("���յ�Ȼ,����˯��!");
		setAnswers("����������������������������������������������������������������������");
		setAnswers("ɵ����ɵ����");
		setAnswers("С����С����,�ٺ�");
		setAnswers("���ʲô");
		setAnswers("������СС����ˣ��");
		setAnswers("������˧!");
		setAnswers("���궼9102����!");
		setAnswers("�ҵ����ֽ�СС����,�ҿ������С�ɰ�ѽ,������");

		setUnknownAnswers("����������˵ʲô");
		setUnknownAnswers("���ܲ��ܺú�������");
		setUnknownAnswers("���������Һ������");
		setUnknownAnswers("���Σ��㲻����Ҫ���Ұɣ�");
		setUnknownAnswers("�û����鲻�԰���");
		setUnknownAnswers("˵������˵���һ�û������");
		setUnknownAnswers("ʲô��˼������ҷ�������");
		setUnknownAnswers("���ǵ��Կ��ˣ������Դ����ˡ���");
		setUnknownAnswers("���˲��ڣ����·����");
		setUnknownAnswers("��λ���࣬���Լ��������Ҵ𰸰ɣ���ϴ������");
		setUnknownAnswers("��˵���Ҷ�����С��������");
		setUnknownAnswers("�����ۣ���Ц�������ۣ����񻨶�����ţ�����Զ��һ��");
		setUnknownAnswers("ҡͷ���ԣ�һ������֪");
		setUnknownAnswers("��˵���Ҷ�����С��������");
		setUnknownAnswers("�Ҷ����٣���֪������˵ʲô��");
		
		oneCharAnswers.add("ʲô,��û����,��˵һ�κò���");
		oneCharAnswers.add("ʲô,�پ����");
		oneCharAnswers.add("?����˵ʲô������˵");
		oneCharAnswers.add("?�Ҳ²������������˼��");
		oneCharAnswers.add("���������������ô��");
		oneCharAnswers.add("?�������˵ʲô�źá�");
		oneCharAnswers.add("?һ���ָ�ë�߰�");
		oneCharAnswers.add("?һ����������ô��");
		
		givenList_Hi.add("���");
		givenList_Hi.add("��ð�");
		givenList_Hi.add("���ѽ");
		givenList_Hi.add("����");
		givenList_Hi.add("����?");
		givenList_Hi.add("��?");
		givenList_Hi.add("��");
		givenList_Hi.add("���");
		givenList_Hi.add("hi");
		givenList_Hi.add("hello");
		
		answersList_Hi.add("�ڵ���");
		answersList_Hi.add("���С�ɰ�������");
		answersList_Hi.add("����С�ɰ�����,��ǩ��һ��");
		answersList_Hi.add("��");
		answersList_Hi.add("���");
		answersList_Hi.add("hi");
		answersList_Hi.add("hello");
		answersList_Hi.add("������������");
		
		givenList_KuaKua.add("�����");
		givenList_KuaKua.add("������");
		givenList_KuaKua.add("�����");
		givenList_KuaKua.add("�������");
		givenList_KuaKua.add("���");
		givenList_KuaKua.add("�����");
		givenList_KuaKua.add("�����");
		givenList_KuaKua.add("�����˧");
		givenList_KuaKua.add("����˧");
		
		answersList_KuaKua.add("����ܸ�Ŷ��");
		answersList_KuaKua.add("�ҿ��㣬˭���Ұ���");
		answersList_KuaKua.add("����˧,,,��ƨ��");
		answersList_KuaKua.add("�������֣�һ�ֺÿ��ģ�һ���ѿ��ģ�������м䣬���ں��ѿ��ġ�");
		answersList_KuaKua.add("����ܸ�Ŷ��");
		
		givenTranslate.add("���ҷ���һ��");
		givenTranslate.add("���ҷ���һ�°�");
		givenTranslate.add("���ҷ���һ�°�");
		givenTranslate.add("���ҷ���һ����");
		givenTranslate.add("����");
		givenTranslate.add("���ҷ���");
		givenTranslate.add("���߷���");
		errorTranslate.add("����һ��");
		
		errorTranslate.add("��,СС��û����");
		errorTranslate.add("���������˼ ����");
		errorTranslate.add("СС����������,���벻����");
		errorTranslate.add("�Դ�崻���,û�з�����");
		errorTranslate.add("���һ�µ�����û��ƴд�����");
		errorTranslate.add("С��ʾ:СС��Ŀǰֻ�ᷭ��Ӣ��Ŷ");
		
		answersTranslate.add("�õ�,��˵");
		answersTranslate.add("��˵������");

	}

}

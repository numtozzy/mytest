package com.operations.test;

import java.util.Random;
import java.util.Scanner;
/**
 *把num1,num2,num3变成全局变量（版本2）
 */
public class Test {
	int result;//把return定义为全局变量，方便pri()方法统计
	int num1,num2,num3;
	public static void main(String[] args) {
		Test t = new Test();
		int n;
		Scanner s = new Scanner(System.in);
		System.out.println("请输入题目的n个数：");
		n = s.nextInt();
		t.operation(n);
	}
	public void operation(int n){
		int op1,op2;
		for (int i = 0; i < n; i++) {
			num1 = new Random().nextInt(100)+1;
			num2 = new Random().nextInt(100)+1;
			num3 = new Random().nextInt(100)+1;
			op1=new Random().nextInt(4)+1;//用1~4随机生成的整数来代表加、减、乘、除四个运算符之一
			op2=new Random().nextInt(4)+1;

			//整数1代表加法
			if(op1 == 1){
				String str = num1 + "+" + num2;//str为输出提示语句（公式）
				if(op2 == 1){
					result = add(num1,num2);
					result = add(result,num3);
					str = str + "+" + num3;
				}else if(op2 == 2){
					result = add(num1,num2);
					//while循环确保此result值大于等于num3，从而保证最终答案不为负数
					while(result < num3){
						num3 = new Random().nextInt(100)+1;
					}
					result = sub(result,num3);
					str = str + "-" + num3;
				}else{
					str = pri(op1,op2);
				}
				print(str);
			//整数2代表减法
			}else if(op1 == 2){
//				String str = num1 + "-" + num2;
				String str = "";
				if(op2 == 1){
					result = sub(num1,num2);
					//while循环确保此result的绝对值小于等于num3的绝对值，从而保证最终答案不为负数
					while(Math.abs(result) > Math.abs(num3)){
						num3 = new Random().nextInt(100)+1;
					}
					result = add(result,num3);
					str = num1 + "-" + num2 + "+" + num3;
				}else if(op2 == 2){
				//方式一：
					//while循环确保result的值为正数（因为如果这一步result为负数，下一步的result值一定为负数(由于num3一定为正数))
					while(num1 < num2){//算法不优，把num1的取值范围缩小了
						num1 = new Random().nextInt(100)+1;
					}
					result = sub(num1,num2);
//					str = num1 + "-" + num2;
					//while循环确保result大于等于num3
					while(result < num3){
						num3 = new Random().nextInt(100)+1;
					}
					result = sub(result,num3);

				/*//方式二：(会出错，因为在100范围内，num2+num3很容易超过一百，以致于num1无论随机生成几次也不可能大于100，造成卡克)
					//while循环确保num1大于等于num2+num3，从而保证最终答案不为负数
					while(num1 < (num2 + num3)){
						num1 = new Random().nextInt(100)+1;
					}
					result = sub(num1,num2);
					result = sub(result,num3);*/
					str = num1 + "-" + num2 + "-" + num3;
				}else{
					str = pri(op1,op2);
				}
				print(str);
			//整数3代表从乘法
			}else if(op1 == 3){
				String str = num1 + "*" + num2;
				result = mul(num1,num2);
				str = operation2(op1,op2,str);
				print(str);
			//整数4代表除法
			}else if(op1 == 4){
				String str = num1 + "/" + num2;
				result = div(num1,num2);
				str = operation2(op1,op2,str);
				print(str);

			}else {
				System.out.println("出错了。。。。");
			}
		}
	}
	public String operation2(int op1,int op2,String str){
		if(op2 == 1){
			result = add(result,num3);
			return str + "+" + num3;
		}else if(op2 == 2){
			//while循环确保此result值小于num1，从而保证最终答案不为负数（当第一个运算符是除法，由于除法结果容易为很小的数，
			// n2只能变到很小的数才符合要求，效率低下，算法不优，容易造成卡壳（一直等待））
			//所以改变result
			while(result < num3){
				num1 = new Random().nextInt(100)+1;
				num2 = new Random().nextInt(100)+1;
				if(op1 == 3){
					result = mul(num1,num2);
					str = num1 + "*" + num2;
				}else{
					result = div(num1,num2);
					str = num1 + "/" + num2;
				}
			}
			result = sub(result,num3);
			return str + "-" + num3;
		}else if(op2 == 3){
			result = mul(result,num3);
			return str + "*" + num3;
		}else{
			result = div(result,num3);
			return str + "/" + num3;
		}
	}
	public int add(int n1,int n2){
		return n1 + n2;
	}
	public int sub(int n1,int n2){
		return n1 - n2;
	}
	public int mul(int n1,int n2){
		return n1 * n2;
	}
	public int div(int n1,int n2){
		return n1 / n2;
	}
	/*
	 * 判断优先级
	**/
	public String pri(int op1,int op2){
		String str = "";
		if(op2 == 3){
			result = mul(num2,num3);
			str = comPri(op1,op2,str);
			str = str + "*" + num3;
		}
		if(op2 == 4){
			result = div(num2,num3);
			str = comPri(op1,op2,str);
			str = str + "/" + num3;
		}
		return str;
	}
	public String comPri(int op1,int op2,String str){
		if(op1 == 1){
			result = add(num1,result);
			str = num1 + "+" + num2;
		}
		if(op1 == 2){
			//while循环确保此result值小于num1，从而保证最终答案不为负数
			// （由于乘法很容易超出100，num1无论如何也赶超不了，所以要让result变）
			while(num1 < result){
				num2 = new Random().nextInt(100)+1;
				num3 = new Random().nextInt(100)+1;
				result = mul(num2,num3);
			}
			result = sub(num1,result);
			str = num1 + "-" + num2;
		}
		return str;
	}
	public void print(String str){
		Scanner s = new Scanner(System.in);
		System.out.print(str + "=");
		int input = s.nextInt();
		if(input == result){
			System.out.println("恭喜您，答对啦~");
		}else {
			System.out.println("真抱歉，回答错误！正确答案为：" + str + "=" +result);
		}
	}
}


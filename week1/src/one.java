import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
计算器
括号里的优先级问题还不知道怎么解决？？？？例如（20+10*4）*4
 */
public class one {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
       // Calculator calculator=new Calculator();
        List<String> list = new ArrayList();
        while(1==1){
            String instr= in.next();
            if(instr!=null){
                list.add(instr);
            }else {
                break;
            }
        }
        int i=0;
        if(list.get(i)=="("){
            while(list.get(i)!=")"){

            }
        }

    }
    public static int kuohao(String str) {
        return 0;
    }
    public static double js(Calculator calculator) {
        char op =calculator.getOp().getOp();
                switch (op) {
                    case '+':
                        return calculator.getNum1() + calculator.getNum2();
                    case '-':
                        return calculator.getNum1() - calculator.getNum2();
                    case '*':
                        return calculator.getNum1() * calculator.getNum2();
                    case '/':
                        return calculator.getNum1() / calculator.getNum2();
                    default:
                        System.out.println("输入异常");
                }
                return 0;
    }
}
class Calculator{
    private double num1;
    private Op op;
    private double num2;
    private double result;
    public Calculator(){

    }

    public double getNum1() {
        return num1;
    }

    public void setNum1(double num1) {
        this.num1 = num1;
    }

    public Op getOp() {
        return op;
    }

    public void setOp(Op op) {
        this.op = op;
    }

    public double getNum2() {
        return num2;
    }

    public void setNum2(double num2) {
        this.num2 = num2;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }
}

class Op{
    private char op;
    public Op(){
        switch (this.op){
            case '+':
            case '-':
            case '*':
            case '/':
            default: throw new RuntimeException("操作符异常");
        }
    }

    public char getOp() {
        return op;
    }

    public void setOp(char op) {
        this.op = op;
    }
}

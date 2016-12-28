package app.calculator.am.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnZero,btnOne,btnTwo,btnThree,btnFour,btnFive,btnSix,btnSeven,btnEight,btnNine,
    btnPlus,btnMinus,btnEquals,btnMultiply,btnDivide,btnCancel;

   TextView display;

    private ArrayList<Float> mathResults = new ArrayList<>();
    private float leftTotal;
    private float rightTotal;

    private int currentOperation = 0;
    private int nextOperation;

    private final static int PLUS = 1;
    private final static int MINUS = 2;
    private final static int MULTIPLY = 3;
    private final static int DIVIDE= 4;
    private final static int EQUALS = 0;


    private final static int CLEAR = 1;
    private final static int DONT_CLEAR = 0;

    private int clearDisplay = 0;
    private String formatParams = "%.3f";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = (TextView)findViewById(R.id.display);

        btnZero = (Button)findViewById(R.id.btnZero);
        btnOne = (Button)findViewById(R.id.btnOne);
        btnTwo = (Button)findViewById(R.id.btnTwo);
        btnThree = (Button)findViewById(R.id.btnThree);
        btnFour = (Button)findViewById(R.id.btnFour);
        btnFive = (Button)findViewById(R.id.btnFive);
        btnSix = (Button)findViewById(R.id.btnSix);
        btnSeven = (Button)findViewById(R.id.btnSeven);
        btnEight = (Button)findViewById(R.id.btnEight);
        btnNine = (Button)findViewById(R.id.btnNine);

        btnPlus = (Button)findViewById(R.id.btnPlus);
        btnMinus = (Button)findViewById(R.id.btnMinus);
        btnMultiply = (Button)findViewById(R.id.btnMultiply);
        btnDivide = (Button)findViewById(R.id.btnDivide);
        btnEquals = (Button)findViewById(R.id.btnEquals);
        btnCancel = (Button)findViewById(R.id.btnCancel);

        display.setKeyListener(DigitsKeyListener.getInstance(true,true));

        registerListeners();

    }
   private void registerListeners(){

       btnZero.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(clearDisplay == CLEAR){
                   display.setText("");
               }
               clearDisplay=DONT_CLEAR;
               display.append("0");
           }
       });

       btnOne.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(clearDisplay == CLEAR){
                   display.setText("");
               }
               clearDisplay=DONT_CLEAR;
               display.append("1");

           }
       });

       btnTwo.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(clearDisplay == CLEAR){
                   display.setText("");
               }
               clearDisplay=DONT_CLEAR;
               display.append("2");
           }
       });

       btnThree.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(clearDisplay == CLEAR){
                   display.setText("");
               }
               clearDisplay=DONT_CLEAR;
               display.append("3");
           }
       });

       btnFour.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(clearDisplay == CLEAR){
                   display.setText("");
               }
               clearDisplay=DONT_CLEAR;
               display.append("4");
           }
       });

       btnFive.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(clearDisplay == CLEAR){
                   display.setText("");
               }
               clearDisplay=DONT_CLEAR;
               display.append("5");
           }
       });

       btnSix.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(clearDisplay == CLEAR){
                   display.setText("");
               }
               clearDisplay=DONT_CLEAR;
               display.append("6");
           }
       });

       btnSeven.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(clearDisplay == CLEAR){
                   display.setText("");
               }
               clearDisplay=DONT_CLEAR;
               display.append("7");
           }
       });


       btnEight.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(clearDisplay == CLEAR){
                   display.setText("");
               }
               clearDisplay=DONT_CLEAR;
               display.append("8");
           }
       });

       btnNine.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(clearDisplay == CLEAR){
                   display.setText("");
               }
               clearDisplay=DONT_CLEAR;
               display.append("9");
           }
       });


       btnPlus.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               calculatorEngine(PLUS);
           }
       });

       btnMinus.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               calculatorEngine(MINUS);
           }
       });

       btnMultiply.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               calculatorEngine(MULTIPLY);
           }
       });

       btnDivide.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               calculatorEngine(DIVIDE);
           }
       });

       btnEquals.setOnClickListener(new View.OnClickListener() {


           @Override
           public void onClick(View view) {
               String chechEmptyDisplay = display.getText().toString();
               if(!chechEmptyDisplay.isEmpty()){
                   calculatorEngine(EQUALS);
               }
           }
       });

       btnCancel.setOnClickListener(new View.OnClickListener() {


           @Override
           public void onClick(View view) {
               display.setText("");
               leftTotal = 0;
               rightTotal = 0;
               mathResults.removeAll(mathResults);
               currentOperation = 0;
               nextOperation = 0;
           }
       });





   }

    private void calculatorEngine(int operator){

        mathResults.add(Float.parseFloat(display.getText().toString()));

        if(operator != EQUALS){
            nextOperation =operator;
        }else if(operator == EQUALS){
            nextOperation = 0;
        }

        switch (currentOperation){
            case PLUS:
                leftTotal=mathResults.get(0);
                rightTotal=mathResults.get(1);

                mathResults.removeAll(mathResults);
                mathResults.add(leftTotal + rightTotal);

                display.setText(String.format(formatParams,mathResults.get(0)));
                break;
            case MINUS:
                leftTotal=mathResults.get(0);
                rightTotal=mathResults.get(1);

                mathResults.removeAll(mathResults);
                mathResults.add(leftTotal - rightTotal);

                display.setText(String.format(formatParams,mathResults.get(0)));
                break;
            case MULTIPLY:
                leftTotal=mathResults.get(0);
                rightTotal=mathResults.get(1);

                mathResults.removeAll(mathResults);
                mathResults.add(leftTotal * rightTotal);

                display.setText(String.format(formatParams,mathResults.get(0)));
                break;
            case DIVIDE:
                leftTotal=mathResults.get(0);
                rightTotal=mathResults.get(1);

                mathResults.removeAll(mathResults);
                mathResults.add(leftTotal / rightTotal);

                display.setText(String.format(formatParams,mathResults.get(0)));
                break;

        }

        clearDisplay =CLEAR;
        currentOperation = nextOperation;
        if(operator==EQUALS){
            leftTotal = 0;
            rightTotal =0;
            mathResults.removeAll(mathResults);

        }

    }

}

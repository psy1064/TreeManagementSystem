package com.example.treemanagement;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class InputActivity extends AppCompatActivity {
    private TextView treeDetailText, finalPriceValue, countValueText;
    private EditText priceEditText, sellCountEditText;
    private Button priceCheckButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        // 화면을 landscape(가로) 화면으로 고정하고 싶은 경우

        setContentView(R.layout.activity_input);

        final Tree tree = (Tree)getIntent().getSerializableExtra("Tree");     // MainActivity에서 넘긴 Class 받음
        final ArrayList<Tree> findTreeList = (ArrayList<Tree>)getIntent().getSerializableExtra("TreeList");

        treeDetailText = (TextView) findViewById(R.id.treeDetailText);
        finalPriceValue = (TextView) findViewById(R.id.finalPriceValue);
        countValueText = (TextView) findViewById(R.id.countValueText);
        priceEditText = (EditText) findViewById(R.id.priceEditText);
        sellCountEditText = (EditText) findViewById(R.id.sellCountEditText);
        priceCheckButton = (Button) findViewById(R.id.priceCheckButton);

        treeDetailText.setText("품명 = " + tree.getName() + "\n" +
                        "수고 = " + tree.getTreeHeight() +
                        "\t\t\t근원직경 = " + tree.getRootCollar() +
                        "\t\t흉고직경 = " + tree.getBreastHeight() +
                        "\n수관폭 = " + tree.getWidthCrown() +
                        "\t\t수관길이 = " + tree.getLength() +
                        "\t\t지하고 = " + tree.getCrownHeight());

        countValueText.setText(String.valueOf(tree.getCount()));

        priceEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int count = Integer.parseInt(String.valueOf(sellCountEditText.getText()));
                int price = Integer.parseInt(String.valueOf(priceEditText.getText()));
                finalPriceValue.setText(String.valueOf(count * price));
            }
        });

        priceCheckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InputActivity.this, PriceCheckActivity.class);
                intent.putExtra("TreeList", findTreeList);
                startActivity(intent);
            }
        });
    }
}

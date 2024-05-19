package com.example.electriccalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText numberElectricity, rebate;
    Button btnCalculate, btnClear;
    TextView electricityOutput, finalCostOutput, rebateOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numberElectricity = findViewById(R.id.numberElectricity);
        rebate = findViewById(R.id.rebate);
        btnCalculate = findViewById(R.id.btnCalaculate);
        btnClear = findViewById(R.id.btnClear);
        electricityOutput = findViewById(R.id.electricityOutput);
        finalCostOutput = findViewById(R.id.finalCostOutput);
        rebateOutput = findViewById(R.id.rebateOutput);

        btnCalculate.setOnClickListener(this);
        btnClear.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnCalaculate) {
            String inputElectricity = numberElectricity.getText().toString();
            String inputRebate = rebate.getText().toString();

            if (TextUtils.isEmpty(inputElectricity) || TextUtils.isEmpty(inputRebate)) {
                Toast.makeText(this, "Please enter values for electricity and rebate", Toast.LENGTH_SHORT).show();
            } else {
                double electricity = Double.parseDouble(inputElectricity);
                double rebateValue = Double.parseDouble(inputRebate);

                if (rebateValue < 1 || rebateValue > 5) {
                    Toast.makeText(this, "Rebate must be between 1% and 5%", Toast.LENGTH_SHORT).show();
                    return;
                }

                double electricityResult = calculateElectricityBill(electricity);
                double finalCost = electricityResult - (electricityResult * (rebateValue / 100.0));

                DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
                decimalFormat.setRoundingMode(RoundingMode.DOWN); // Set rounding mode to down

                String formattedElectricityResult = decimalFormat.format(electricityResult / 100.0);
                String formattedFinalCost = decimalFormat.format(finalCost / 100.0);

                String electricityText = "Electricity Bill: RM" + formattedElectricityResult;
                String finalCostText = "Final Cost: RM" + formattedFinalCost;
                String rebateText = "Rebate: " + rebateValue + "%";

                electricityOutput.setText(electricityText);
                finalCostOutput.setText(finalCostText);
                rebateOutput.setText(rebateText);

                Toast.makeText(this, finalCostText, Toast.LENGTH_SHORT).show();
            }
        } else if (v.getId() == R.id.btnClear) {
            numberElectricity.setText("");
            rebate.setText("");
            electricityOutput.setText("Bill: RM 0.00");
            finalCostOutput.setText("Final Cost: RM 0.00");
            rebateOutput.setText("Rebate: 0%");
        }
    }

    private double calculateElectricityBill(double electricity) {
        double totalCharge = 0.0;

        if (electricity <= 200) {
            totalCharge = electricity * 21.8;
        } else if (electricity <= 300) {
            totalCharge = (200 * 21.8) + ((electricity - 200) * 33.4);
        } else if (electricity <= 600) {
            totalCharge = (200 * 21.8) + (100 * 33.4) + ((electricity - 300) * 51.6);
        } else if (electricity > 600) {
            totalCharge = (200 * 21.8) + (100 * 33.4) + (300 * 51.6) + ((electricity - 600) * 54.6);
        }

        return totalCharge;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.about) {
            Intent aboutIntent = new Intent(this, AboutActivity.class);
            startActivity(aboutIntent);
            return true;
        } else if (itemId == R.id.instruction) {
            Intent instructionsIntent = new Intent(this, ProfileActivity.class);
            startActivity(instructionsIntent);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}

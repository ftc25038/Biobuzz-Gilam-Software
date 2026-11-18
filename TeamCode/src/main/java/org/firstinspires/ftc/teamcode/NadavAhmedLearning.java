package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;



@Autonomous
public class NadavAhmedLearning extends OpMode {

    DcMotor myMotor;

    DcMotor motor;

    // Types of Variables
    int number = 3; // Whole Numbers
    double motor_power = 0.5; // Decimal Numbers
    char letter = 'a'; // Only Single Characters
    String name = "Ahmed"; // Full text and Sentences
    boolean isTrue = true; // True or False


    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");
        myMotor = hardwareMap.dcMotor.get("motor1");
        myMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        myMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        motor = hardwareMap.dcMotor.get("motor2");

    }

    @Override
    public void loop() {
        telemetry.addData("Status", "Running");
        myMotor.setPower(gamepad1.right_trigger);


    }
}

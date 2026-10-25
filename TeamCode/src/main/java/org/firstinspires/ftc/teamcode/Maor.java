package org.firstinspires.ftc.teamcode;

// import com.acmerobotics.dashboard.FtcDashboard;
// import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
// import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;


@TeleOp
public class Maor extends OpMode {
    // Declare Control Hub Devices
    private DcMotor frr = null; // 0 on Control Hub
    private DcMotor bkr = null; // 1 on Contrul Hub
    private DcMotor picker = null; // 2 on Control Hub

    // Declare Expansion Hub Devices
    private DcMotor frl = null; // 0 on Expansion Hub 2
    private DcMotor bkl = null; // 1 on Expansion Hub 2

    // Declare Timer
    private ElapsedTime runtime = new ElapsedTime();


    @Override
    public void init() {
        // Initialize Hardware Map
        frr = hardwareMap.dcMotor.get("frr");
        frl = hardwareMap.dcMotor.get("frl");
        bkr = hardwareMap.dcMotor.get("bkr");
        picker = hardwareMap.dcMotor.get("picker");
        bkl = hardwareMap.dcMotor.get("bkl");

        runtime.reset(); // reset timer
        telemetry.addData("Init Started, Time: ", "%.2f seconds");

        // Set motor directions (reverse one side so all go forward together)
        // Adjust these directions based on how your specific chassis is wired
        frl.setDirection(DcMotorSimple.Direction.REVERSE);
        bkl.setDirection(DcMotorSimple.Direction.REVERSE);
        frr.setDirection(DcMotorSimple.Direction.FORWARD);
        bkr.setDirection(DcMotorSimple.Direction.FORWARD);

        picker.setDirection(DcMotorSimple.Direction.FORWARD);

        // Set zero power behavior to BRAKE (stops robot from coasting)
        frl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bkl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bkr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        picker.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void loop() {
        telemetry.addData("Loop Started, Time: ", "%.2f seconds", runtime.seconds());

        // Read gamepad axes
        // Note: Y stick values are typically inverted on gamepads (pushing forward is negative)
        double y = -gamepad1.left_stick_y; // Remember: Y is forward/backward
        double x = gamepad1.left_stick_x;  // Strafe left/right
        double rx = gamepad1.right_stick_x; // Turn left/right


        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all powers scale proportionally if any exceed 1.0
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1.0);

        double frontLeftPower  = (y + x + rx) / denominator;
        double backLeftPower   = (y - x + rx) / denominator;
        double frontRightPower = (y - x - rx) / denominator;
        double backRightPower  = (y + x - rx) / denominator;

        // Apply powers to the motors
        frl.setPower(frontLeftPower);
        bkl.setPower(backLeftPower);
        frr.setPower(frontRightPower);
        bkr.setPower(backRightPower);

        if (gamepad1.right_bumper) {
            picker.setPower(1);
        } else if (gamepad1.left_bumper) {
            picker.setPower(-1);
        } else {
            picker.setPower(0);
        }

        // Driving Controls using D-Pad
        if (gamepad1.dpad_up) {
            frl.setPower(0.5);
            bkl.setPower(0.5);
            frr.setPower(0.5);
            bkr.setPower(0.5);
        }
        if (gamepad1.dpad_down) {
            frl.setPower(-0.5);
            bkl.setPower(-0.5);
            frr.setPower(-0.5);
            bkr.setPower(-0.5);
        }
        if (gamepad1.dpad_left) {
            frl.setPower(-0.5);
            bkl.setPower(0.5);
            frr.setPower(0.5);
            bkr.setPower(-0.5);
        }
        if (gamepad1.dpad_right) {
            frl.setPower(0.5);
            bkl.setPower(-0.5);
            frr.setPower(-0.5);
            bkr.setPower(0.5);
        }

        // Feedback
        telemetry.addData("Motors", "FL (%.2f), BL (%.2f), FR (%.2f), BR (%.2f)",
                frontLeftPower, backLeftPower, frontRightPower, backRightPower);
        telemetry.update();


    }
}

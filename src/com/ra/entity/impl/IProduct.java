package com.ra.entity.impl;

import java.util.Scanner;

public interface IProduct {
    float MIN_INTEREST_RATE = 0.2f;
    void inputData(Scanner sc);
    void displayData();
    void calProfit();
}

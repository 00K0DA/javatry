/*
 * Copyright 2019-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.docksidestage.javatry.basic;

import org.docksidestage.bizfw.basic.buyticket.Ticket;
import org.docksidestage.bizfw.basic.buyticket.TicketBooth;
import org.docksidestage.bizfw.basic.buyticket.TicketBooth.TicketShortMoneyException;
import org.docksidestage.unit.PlainTestCase;

/**
 * The test of class. <br>
 * Operate exercise as javadoc. If it's question style, write your answer before test execution. <br>
 * (javadocの通りにエクササイズを実施。質問形式の場合はテストを実行する前に考えて答えを書いてみましょう)
 * @author jflute
 * @author ookoda
 */
public class Step05ClassTest extends PlainTestCase {

    // ===================================================================================
    //                                                                          How to Use
    //                                                                          ==========
    /**
     * What string is sea variable at the method end? <br>
     * (メソッド終了時の変数 sea の中身は？)
     */
    public void test_class_howToUse_basic() {
        TicketBooth booth = new TicketBooth();
        booth.buyOneDayPassport(7400);
        int sea = booth.getQuantity();
        log(sea); // your answer? => 9
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_class_howToUse_overpay() {
        TicketBooth booth = new TicketBooth();
        booth.buyOneDayPassport(10000);
        Integer sea = booth.getSalesProceeds();
        log(sea); // your answer? => 7400
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_class_howToUse_nosales() {
        TicketBooth booth = new TicketBooth();
        Integer sea = booth.getSalesProceeds();
        log(sea); // your answer? => null
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_class_howToUse_wrongQuantity() {
        Integer sea = doTest_class_ticket_wrongQuantity();
        log(sea); // your answer? => 10
    }

    private Integer doTest_class_ticket_wrongQuantity() {
        TicketBooth booth = new TicketBooth();
        int handedMoney = 7399;
        try {
            booth.buyOneDayPassport(handedMoney);
            fail("always exception but none");
        } catch (TicketShortMoneyException continued) {
            log("Failed to buy one-day passport: money=" + handedMoney, continued);
        }
        return booth.getQuantity();
    }

    // ===================================================================================
    //                                                                           Let's fix
    //                                                                           =========
    /**
     * Fix the problem of ticket quantity reduction when short money. (Don't forget to fix also previous exercise answers) <br>
     * (お金不足でもチケットが減る問題をクラスを修正して解決しましょう (以前のエクササイズのanswerの修正を忘れずに))
     */
    public void test_class_letsFix_ticketQuantityReduction() {
        Integer sea = doTest_class_ticket_wrongQuantity();
        log(sea); // should be max quantity, visual check here 10
    }

    /**
     * Fix the problem of sales proceeds increased by handed money. (Don't forget to fix also previous exercise answers) <br>
     * (受け取ったお金の分だけ売上が増えていく問題をクラスを修正して解決しましょう (以前のエクササイズのanswerの修正を忘れずに))
     */
    public void test_class_letsFix_salesProceedsIncrease() {
        TicketBooth booth = new TicketBooth();
        booth.buyOneDayPassport(10000);
        Integer sea = booth.getSalesProceeds();
        log(sea); // should be same as one-day price, visual check here 7400
    }

    /**
     * Make method for buying two-day passport (price is 13200). (which can return change as method return value)
     * (TwoDayPassport (金額は13200) も買うメソッドを作りましょう (戻り値でお釣りをちゃんと返すように))
     */
    public void test_class_letsFix_makeMethod_twoday() {
        // comment out after making the method
        TicketBooth booth = new TicketBooth();
        int money = 14000;
        Ticket ticket = booth.buyTwoDayPassport(money);
        Integer sea = booth.getSalesProceeds() + ticket.getChange();
        log(sea); // should be same as money

        // and show two-day passport quantity here
        log(booth.getQuantityTwo());
    }

    /**
     * Recycle duplicate logics between one-day and two-day by e.g. private method in class. (And confirm result of both before and after) <br>
     * (OneDayとTwoDayで冗長なロジックがあったら、クラス内のprivateメソッドなどで再利用しましょう (修正前と修正後の実行結果を確認))
     */
    public void test_class_letsFix_refactor_recycle() {
        TicketBooth booth = new TicketBooth();
        booth.buyOneDayPassport(10000);
        log(booth.getQuantity(), booth.getSalesProceeds()); // should be same as before-fix 9,7400
        log(booth.getQuantity(), booth.getSalesProceeds()); // after 9,7400
    }

    // ===================================================================================
    //                                                                           Challenge
    //                                                                           =========
    /**
     * Now you cannot get ticket if you buy one-day passport, so return Ticket class and do in-park. <br>
     * (OneDayPassportを買ってもチケットをもらえませんでした。戻り値でTicketクラスを戻すようにしてインしましょう)
     */
    public void test_class_moreFix_return_ticket() {
        // comment out after modifying the method
        TicketBooth booth = new TicketBooth();
        Ticket ticket = booth.buyOneDayPassport(10000); //edited_line
        log(booth.getQuantity());
        log(ticket.getTicketPrice()); // should be same as one-day price
        log(ticket.isAlreadyIn()); // should be false
        ticket.doInPark();
        log(ticket.isAlreadyIn()); // should be true
        log(ticket.getTicketType()); //add_method
    }

    /**
     * Now also you cannot get ticket if two-day passport, so return class that has ticket and change. <br>
     * (TwoDayPassportもチケットをもらえませんでした。チケットとお釣りを戻すクラスを作って戻すようにしましょう)
     */
    public void test_class_moreFix_return_whole() {
        //         comment out after modifying the method
        TicketBooth booth = new TicketBooth();
        int handedMoney = 20000;
        Ticket ticket = booth.buyTwoDayPassport(handedMoney);
        int change = ticket.getChange();
        log(ticket.getTicketPrice() + change); // should be same as money
        log(ticket.getTicketType()); //add_method
    }

    /**
     * Now you cannot judge ticket type "one-day or two-day?", so add method to judge it. <br>
     * (チケットをもらってもOneDayなのかTwoDayなのか区別が付きません。区別を付けられるメソッドを追加しましょう)
     */
    public void test_class_moreFix_type() {
        TicketBooth booth = new TicketBooth();
        int handedMoney = 20000;
        Ticket ticket = booth.buyTwoDayPassport(handedMoney);
        int change = ticket.getChange();
        log(ticket.getTicketPrice() + change); // should be same as money
        log(ticket.getTicketType()); //add_method
    }

    // ===================================================================================
    //                                                                           Good Luck
    //                                                                           =========
    /**
     * Now only one use with two-day passport, so split ticket in one-day and two-day class and use interface. <br>
     * <pre>
     * o change Ticket class to interface, define doInPark(), getDisplayPrice() in it
     * o make class for one-day and class for plural days (called implementation class)
     * o make implementation classes implement Ticket interface
     * o doInPark() of plural days can execute defined times
     * </pre>
     * (TwoDayのチケットが一回しか利用できません。OneDayとTwoDayのクラスを分けてインターフェースを使うようにしましょう)
     * <pre>
     * o Ticket をインターフェース(interface)にして、doInPark(), getDisplayPrice() を定義
     * o OneDay用のクラスと複数日用のクラスを作成 (実装クラスと呼ぶ)
     * o 実装クラスが Ticket を implements するように
     * o 複数日用のクラスでは、決められた回数だけ doInPark() できるように
     * </pre>
     */

    public void test_class_moreFix_useInterface() {
        TicketBooth booth = new TicketBooth();
        Ticket oneDayTicket = booth.buyOneDayPassport(20000);
        Ticket twoDayTicket = booth.buyTwoDayPassport(20000);
        // oneDayTicket.doOutPark();
        // Error → まだ入ってないぞ7400
        log(oneDayTicket.isAlreadyIn());
        // →false

        log(oneDayTicket.getTicketPrice());
        // →7400

        log(oneDayTicket.getChange());
        // →12600

        oneDayTicket.doInPark();

        log(oneDayTicket.isAlreadyIn());
        // →true

        // oneDayTicket.doInPark(); 
        // Error → Already in park by this ticket: displayedPrice=7400

        oneDayTicket.doOutPark();

        // oneDayTicket.doInPark();
        // Error → このチケットは使えません。7400

        log("\n↓twoDayTicketTest↓\n");

        log(twoDayTicket.isAlreadyIn());
        // →false

        log(twoDayTicket.getTicketPrice());
        // →13200

        log(twoDayTicket.getChange());
        // →6800

        twoDayTicket.doInPark();

        log(twoDayTicket.isAlreadyIn());
        // →true

        twoDayTicket.doOutPark();
        twoDayTicket.doInPark();

        twoDayTicket.doOutPark();
        // twoDayTicket.doInPark();
        // Error → このチケットは使えません。13200
    }

    /**
     * Fix it to be able to buy four-day passport (price is 22400). <br>
     * (FourDayPassport (金額は22400) のチケットも買えるようにしましょう)
     */
    public void test_class_moreFix_wonder() {
        TicketBooth booth = new TicketBooth();
        Ticket ticket = booth.buyFourDayPassport(50000);
        log("もう入園してる？       → " + ticket.isAlreadyIn());
        log("チケットは何買った？ → " + ticket.getTicketType());
        log("チケットはいくらした？ → " + ticket.getTicketPrice());
        log("おつりはいくら？         → " + ticket.getChange() + "\n");
        for (int i = 0; i < 5; i++) {
            try {
                ticket.doInPark();
                log(i + 1 + "回目の来園");
                if (ticket.isAlreadyIn()) {
                    log("ﾊﾊｯ");
                }
                log(i + 1 + "回目の帰宅\n");
                ticket.doOutPark();
            } catch (IllegalStateException e) {
                log("帰れ！！！");
            }
        }
    }

    /**
     * Refactor if you want to fix (e.g. is it well-balanced name of method and variable?). <br>
     * (その他、気になるところがあったらリファクタリングしてみましょう (例えば、バランスの良いメソッド名や変数名になっていますか？))
     */
    public void test_class_moreFix_yourRefactoring() {
        // write confirmation code here
    }
}

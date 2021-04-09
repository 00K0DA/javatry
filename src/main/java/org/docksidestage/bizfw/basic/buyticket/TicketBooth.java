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
package org.docksidestage.bizfw.basic.buyticket;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jflute
 * @author ookoda
 */

//  業務要件
//  お金があるかないか分からない人が多数、買いに来る
//  お金が足りない場合も例外としない

public class TicketBooth {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    private static final int MAX_QUANTITY = 10;
    private static final int ONE_DAY_PRICE = 7400; // when 2019/06/15
    private static final int TWO_DAY_PRICE = 13200;
    private static final int FOUR_DAY_PRICE = 22400;
    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private int quantityOne = MAX_QUANTITY;
    private int quantityTwo = MAX_QUANTITY;
    private Map<String, Integer> quantityMap = new HashMap<String, Integer>() {
        {
            put("ONE_DAY", MAX_QUANTITY);
            put("TWO_DAY", MAX_QUANTITY);
            put("FOUR_DAY", MAX_QUANTITY);
        }
    };

    private Integer salesProceeds = 0;
    // 思い出
    //    public class Passport {
    //        protected int price;
    //        protected int usageCount;
    //        protected String ticketType;
    //
    //        public int getPrice() {
    //            return this.price;
    //        }
    //
    //        public int getUsageCount() {
    //            return this.usageCount;
    //        }
    //
    //        public String getTicketType() {
    //            return this.ticketType;
    //        }
    //    }
    //
    //    public class OneDayPassport extends Passport {
    //        public OneDayPassport() {
    //            this.price = ONE_DAY_PRICE;
    //            this.usageCount = 1;
    //            this.ticketType = "ONE_DAY";
    //        }
    //    }
    //
    //    public class TwoDayPassport extends Passport {
    //        public TwoDayPassport() {
    //            this.price = TWO_DAY_PRICE;
    //            this.usageCount = 2;
    //            this.ticketType = "TWO_DAY";
    //        }
    //    }

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public TicketBooth() {
    }

    // ===================================================================================
    //                                                                          Buy Ticket
    //                                                                          ==========
    // 思い出
    //    public Ticket buyOneDayPassport(int handedMoney) {
    //        int change = handedMoney - ONE_DAY_PRICE;
    //        if (change < 0) {
    //            throw new TicketShortMoneyException("Short money: " + handedMoney);
    //        } else {
    //            if (quantity <= 0) {
    //                throw new TicketSoldOutException("Sold out");
    //            } else {
    //                --quantity;
    //                salesProceeds += ONE_DAY_PRICE;
    //                return new Ticket(ONE_DAY_PRICE);
    //            }
    //        }
    //    }
    //    public int buyTwoDayPassport(int handedMoney) {
    //        int change = handedMoney - TWO_DAY_PRICE;
    //        if (change < 0) {
    //            throw new TicketShortMoneyException("Short money: " + handedMoney);
    //        } else {
    //            if (quantityTwo <= 0) {
    //                throw new TicketSoldOutException("Sold out");
    //            } else {
    //                --quantityTwo;
    //                return change;
    //            }
    //        }
    //    }

    public Ticket buyOneDayPassport(int handedMoney) {
        OneDayTicket ticket = new OneDayTicket(ONE_DAY_PRICE, handedMoney, ONE_DAY_PRICE);
        internalBuyPassport(ticket);
        return ticket;
    }

    public Ticket buyTwoDayPassport(int handedMoney) {
        TwoDayTicket ticket = new TwoDayTicket(TWO_DAY_PRICE, handedMoney, TWO_DAY_PRICE);
        internalBuyPassport(ticket);
        return ticket;
    }

    public Ticket buyFourDayPassport(int handedMoney) {
        FourDayTicket ticket = new FourDayTicket(FOUR_DAY_PRICE, handedMoney, FOUR_DAY_PRICE);
        internalBuyPassport(ticket);
        return ticket;
    }

    private void internalBuyPassport(Ticket ticket) {
        assertTicketExsits(ticket.getTicketType());
        // なぜここでif-returnしているのか分からない
        if (ticket.getUsableCount() == 0) {//　isUsable
            return;
        }
        salesProceeds += ticket.getTicketPrice();// DisplayPriceで計算するの？ Regularpriceを使えば？　表示と内部管理はずれやすい
        quantityMap.put(ticket.getTicketType(), (int) quantityMap.get(ticket.getTicketType()) - 1);
    }

    private void assertTicketExsits(String ticketType) {
        if (!quantityMap.containsKey(ticketType)) {
            // エラーメッセージはデバックができる様に！！
            throw new IllegalStateException("知らないticketTypeです。:" + ticketType);
        }
        if (quantityMap.get(ticketType) <= 0) {
            throw new TicketSoldOutException("Sold out");
        }
    }
    // 思い出
    //    public static class TicketBuyResult {
    //        private final Ticket ticket;
    //        private final int change;
    //
    //        public TicketBuyResult(Ticket ticket, int change) {
    //            this.change = change;
    //            this.ticket = ticket;
    //        }
    //
    //        public Ticket getTicket() {
    //            return this.ticket;
    //        }
    //
    //        public int getChange() {
    //            return this.change;
    //        }
    //    }

    public static class TicketSoldOutException extends RuntimeException {

        private static final long serialVersionUID = 1L;

        public TicketSoldOutException(String msg) {
            super(msg);
        }
    }

    public static class TicketShortMoneyException extends RuntimeException {

        private static final long serialVersionUID = 1L;

        public TicketShortMoneyException(String msg) {
            super(msg);
        }
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public int getQuantity() {
        return quantityOne;
    }

    public int getQuantityTwo() {
        return quantityTwo;
    }

    public Integer getSalesProceeds() {
        return salesProceeds;
    }

}

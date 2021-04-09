package org.docksidestage.bizfw.basic.buyticket;

/**
 * @author ookoda
 */
public class FourDayTicket implements Ticket {
    //    ===========================================================================
    private int USABLE_COUNT = 4;
    private String TICKET_TYPE = "FOUR_DAY";
    //    ===========================================================================
    private final int ticketPrice;
    private final int regularPrice;
    private boolean alreadyIn; //状態
    private int usableCount;
    private int change;

    FourDayTicket(int ticketPrice, int handedMoney, int regularPrice) {
        // TODO この辺の処理を再利用したい superClassを作成等をやるよ！
        // Step6学べます
        this.ticketPrice = ticketPrice;
        this.regularPrice = regularPrice;
        if (handedMoney >= ticketPrice) {
            change = handedMoney - ticketPrice;
            usableCount = USABLE_COUNT;
        } else {
            change = handedMoney;
            usableCount = 0;
        }
    }

    public void doInPark() {
        if (alreadyIn) {
            throw new IllegalStateException("Already in park by this ticket: displayedPrice=" + ticketPrice);
        }
        if (usableCount <= 0) {
            throw new IllegalStateException("このチケットはもう使えません。" + ticketPrice);
        }
        if (usableCount == 0) {
            alreadyIn = true;
        }
        usableCount -= 1;
    }

    @Override
    public void doOutPark() {
        if (!alreadyIn) {
            throw new IllegalStateException("まだ入ってないぞ" + ticketPrice);
        }
        // 仕様変更
        //        alreadyIn = false;
    }

    public int getTicketPrice() {
        return ticketPrice;
    }

    public int getRegularPrice() {
        return regularPrice;
    }

    // STEP6でエラーが出るため
    public int getDisplayPrice() {
        return ticketPrice;
    }

    public int getChange() {
        return change;
    }

    public int getUsableCount() {
        return usableCount;
    }

    @Override
    public boolean isAlreadyIn() {
        return alreadyIn;
    }

    @Override
    public String getTicketType() {
        // TODO Auto-generated method stub
        return TICKET_TYPE;
    }
}
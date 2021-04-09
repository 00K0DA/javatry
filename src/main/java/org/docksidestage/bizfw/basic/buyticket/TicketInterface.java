package org.docksidestage.bizfw.basic.buyticket;

public interface TicketInterface {

    int getDisplayPrice();

    int getCharge();

    int getUsableCount();

    void doInPark();
}

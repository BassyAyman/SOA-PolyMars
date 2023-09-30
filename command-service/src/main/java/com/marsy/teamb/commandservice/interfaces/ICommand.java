package com.marsy.teamb.commandservice.interfaces;

public interface ICommand {

    String readinessPoll();
    void launchRocket();
    void processVerificationDestruction(boolean value);

}

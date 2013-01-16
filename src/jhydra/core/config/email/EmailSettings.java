package jhydra.core.config.email;

import jhydra.core.config.exceptions.InvalidEmailAddressException;
import jhydra.core.exceptions.FatalException;
import jhydra.core.properties.IProperties;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * User: jantic
 * Date: 1/10/13
 */

//TODO:  Use a non smtp based email sending program, to minimize complication.
//TODO:  This should probably be something that's set up in a wizard.  Until then, just don't send emails
public class EmailSettings implements IEmailSettings {
    private final List<InternetAddress> failureRecipients;
    private final List<InternetAddress> successRecipients;
    private final InternetAddress sender;

    public EmailSettings(IProperties properties) throws FatalException{
        this.failureRecipients = parseFailureRecipients(properties);
        this.successRecipients = parseSuccessRecipients(properties);
        this.sender = parseSender(properties);
    }

    @Override
    public List<InternetAddress> getFailureRecipients(){
        return this.failureRecipients;
    }

    @Override
    public List<InternetAddress> getSuccessRecipients(){
        return this.successRecipients;
    }

    @Override
    public InternetAddress getSender(){
        return this.sender;
    }

    private List<InternetAddress> parseFailureRecipients(IProperties properties) throws FatalException{
        final String key = getFailureRecipientsKey();
        if(properties.hasProperty(key)){
            final String emailsString = properties.getProperty(key);
            return convertToEmailAddressObjects(emailsString);
        }

        return new ArrayList<>();
    }

    private List<InternetAddress> convertToEmailAddressObjects(String emailsString){
        final String[] emailAddressStrings = emailsString.split(",");
        final List<InternetAddress> emailAddressList = new ArrayList<>();

        for(String emailAddressString : emailAddressStrings){
            emailAddressString = emailAddressString.trim();
            try{
                if(!emailAddressString.isEmpty()){
                    final InternetAddress internetAddress = new InternetAddress(emailAddressString);
                    emailAddressList.add(internetAddress);
                }
            }
            catch(AddressException e){
                //Just skip over it, log a warning.
                //TODO:  Log a warning here.
            }
        }

        return emailAddressList;
    }

    private String getFailureRecipientsKey(){
        return "Email.FailureRecipients";
    }

    private List<InternetAddress> parseSuccessRecipients(IProperties properties) throws FatalException{
        final String key = getSuccessRecipientsKey();
        if(properties.hasProperty(key)){
            final String emailsString = properties.getProperty(getSuccessRecipientsKey());
            return convertToEmailAddressObjects(emailsString);
        }

        return new ArrayList<>();
    }

    private String getSuccessRecipientsKey(){
        return "Email.SuccessRecipients";
    }

    private InternetAddress parseSender(IProperties properties) throws FatalException{
        final String key = getSenderKey();
        String senderEmailString = "";

        try{
            if(properties.hasProperty(key)){
                senderEmailString = properties.getProperty(getSenderKey());
                return new InternetAddress(senderEmailString);
            }
        }
        catch(AddressException e){
            //We can't send an email without a sender.
            throw new InvalidEmailAddressException(senderEmailString, e.getMessage());
        }

        return new InternetAddress();
    }

    private String getSenderKey(){
        return "Email.Sender";
    }
}

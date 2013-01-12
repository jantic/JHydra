package jhydra.core.config.email;

import jhydra.core.config.exceptions.ConfiguredPathNotValidException;
import jhydra.core.config.exceptions.InvalidEmailAddressException;
import jhydra.core.exceptions.FatalException;
import jhydra.core.properties.IProperties;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: jantic
 * Date: 1/10/13
 */

//TODO:  This should probably be something that's set up in a wizard (esp with the smtp host business). Until then, just don't send emails
public class EmailSettings implements IEmailSettings {
    private final List<InternetAddress> failureRecipients;
    private final List<InternetAddress> successRecipients;
    private final InternetAddress sender;
    private final URI smtpHost;
    private final Boolean sendSuccessEmail;
    private final Boolean sendFailureEmail;


    //Package access, instantiated via factory
    EmailSettings(IProperties properties) throws FatalException{
        this.failureRecipients = parseFailureRecipients(properties);
        this.successRecipients = parseSuccessRecipients(properties);
        this.sender = parseSender(properties);
        this.smtpHost = parseSmtpHost(properties);
        this.sendSuccessEmail = parseSendSuccessEmail(properties);
        this.sendFailureEmail = parseSendFailureEmail(properties);
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

    @Override
    public URI getSmtpHost(){
        return this.smtpHost;
    }

    @Override
    public Boolean sendSuccessEmail(){
        return this.sendSuccessEmail;
    }

    @Override
    public Boolean getSendFailureEmail(){
        return this.sendFailureEmail;
    }



    private List<InternetAddress> parseFailureRecipients(IProperties properties) throws FatalException{
        final String emailsString = properties.getProperty(getFailureRecipientsKey());
        return convertToEmailAddressObjects(emailsString);
    }

    private List<InternetAddress> convertToEmailAddressObjects(String emailsString) throws FatalException{
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
                throw new InvalidEmailAddressException(emailAddressString, e.getMessage());
            }
        }

        return emailAddressList;
    }

    private String getFailureRecipientsKey(){
        return "Email.FailureRecipients";
    }

    private List<InternetAddress> parseSuccessRecipients(IProperties properties) throws FatalException{
        final String emailsString = properties.getProperty(getSuccessRecipientsKey());
        return convertToEmailAddressObjects(emailsString);
    }

    private String getSuccessRecipientsKey(){
        return "Email.SuccessRecipients";
    }

    private InternetAddress parseSender(IProperties properties) throws FatalException{
        final String senderEmailString = properties.getProperty(getSenderKey());

        try{
            return new InternetAddress(senderEmailString);
        }
        catch(AddressException e){
            throw new InvalidEmailAddressException(senderEmailString, e.getMessage());
        }
    }

    private String getSenderKey(){
        return "Email.Sender";
    }

    private URI parseSmtpHost(IProperties properties) throws FatalException{
        final String smtpHostString = properties.getProperty(getSmtpHostKey());

        try{

            return new URI(smtpHostString);
        }
        catch(URISyntaxException e){
            throw new ConfiguredPathNotValidException(getSmtpHostKey(), smtpHostString);
        }
    }

    private String getSmtpHostKey(){
        return "Email.SMTPHost";
    }

    private Boolean parseSendSuccessEmail(IProperties properties) throws FatalException{
        final String value = properties.getProperty(getSendSuccessEmailKey());
        return parseBoolean(value);
    }

    private Boolean parseBoolean(String value){
        return value.equalsIgnoreCase("Y") || value.equalsIgnoreCase("true") || value.equalsIgnoreCase("Yes");
    }

    private String getSendSuccessEmailKey(){
        return "Email.SendForSucccess";
    }

    private Boolean parseSendFailureEmail(IProperties properties) throws FatalException{
        final String value = properties.getProperty(getSendFailureEmailKey());
        return parseBoolean(value);
    }

    private String getSendFailureEmailKey(){
        return "Email.SendForFailure";
    }
}

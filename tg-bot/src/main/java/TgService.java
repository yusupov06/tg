

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class TgService extends TelegramLongPollingBot {
    SendMessage message = new SendMessage();
    Map<Long, Integer> userState = new HashMap<>();

    @Override
    public void onUpdateReceived(Update update) {

        Long chatId = update.getMessage().getChatId();
        saveToFile(chatId, update.getMessage().getChat().getUserName(), update.getMessage().getText());
        userState.putIfAbsent(chatId, 1);
        String text = update.getMessage().getText();
        if (text == null) {
            text = "menu";
        }
        switch (text) {
            case "/start":
                getContact(update);
                break;
            case "menu":
                menu(chatId);
                break;
            case "main menu":
                userState.put(chatId, 2);
                mainMenu(chatId);
                break;
            case "drinks":
                userState.put(chatId, 5);
                drinks(chatId);
                break;
            case "Add new card":
                addNewCard(update);
                break;
            case "back":
                backMenu(userState.get(chatId), chatId);
                break;
            case "Transactions":
                forTransactions(update);
                break;
            case "My Cards":
                    myCards(update);
                break;
        }
    }

    private void myCards(Update update) {
            ReplyKeyboardMarkup replyKeyboardMarkup1= new ReplyKeyboardMarkup();
            SendMessage sendMessage1= new SendMessage();
            sendMessage1.setReplyMarkup(replyKeyboardMarkup1);
            replyKeyboardMarkup1.setSelective(true);
            replyKeyboardMarkup1.setOneTimeKeyboard(true);
            replyKeyboardMarkup1.setResizeKeyboard(true);
            List<KeyboardRow> keyboardRows1=new ArrayList<>();
            KeyboardRow rowa=new KeyboardRow();
            KeyboardRow rowb=new KeyboardRow();
            KeyboardRow rowc=new KeyboardRow();
            rowa.add("\uD83D\uDCB3 your cards");
            rowb.add("\uD83D\uDCB3 card 1");
            rowc.add("\uD83D\uDCB3 card 2");
            keyboardRows1.add(rowa);
            keyboardRows1.add(rowb);
            keyboardRows1.add(rowc);
            replyKeyboardMarkup1.setKeyboard(keyboardRows1);
            sendMessage1.setReplyMarkup(replyKeyboardMarkup1);
            sendMessage1.setText("kartalaringiz \uD83D\uDCB3");
            sendMessage1.setChatId(update.getMessage().getChatId().toString());
            try {
                execute(sendMessage1);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }


    private void forTransactions(Update update) {
        if (update.hasMessage()) {
            if (update.getMessage().getText().equals("Transactions")) {
                SendMessage sendMessage = new SendMessage();

                sendMessage.setText("O'tkazmalar turini tanlang\uD83D\uDC47\uD83C\uDFFB");
                InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
                //*********************************************************************************
                InlineKeyboardButton button1 = new InlineKeyboardButton();
                button1.setText("\uD83D\uDCF2Telefon raqami orqali");
                button1.setCallbackData("orqali");
                //*********************************************************************************
                InlineKeyboardButton button2 = new InlineKeyboardButton();
                button2.setText("\uD83D\uDCB3Karta Raqami orqali");
                button2.setCallbackData("main2");
                //*********************************************************************************
                InlineKeyboardButton button3 = new InlineKeyboardButton();
                button3.setText("\uD83D\uDD01O'z kartalarim orasida");
                button3.setCallbackData("main3");
                //*********************************************************************************
                InlineKeyboardButton button4 = new InlineKeyboardButton();
                button4.setText("\uD83D\uDCACTelegram Chat orqali");
                button4.setCallbackData("tgfg");
                //*********************************************************************************
                InlineKeyboardButton button5 = new InlineKeyboardButton();
                button5.setText("⬇️Pul so'rash");
                button5.setCallbackData("gsdg");
                //*********************************************************************************
                InlineKeyboardButton button6 = new InlineKeyboardButton();
                button6.setText("\uD83C\uDF2BQR - Kod orqali");
                button6.setCallbackData("gsdgf");
                //*********************************************************************************
                InlineKeyboardButton button7 = new InlineKeyboardButton();
                button7.setText("\uD83C\uDF2BMening QR-Kodim");
                button7.setCallbackData("gsdg");
                //*********************************************************************************
                List<InlineKeyboardButton> buttonList1 = new ArrayList<>();
                buttonList1.add(button1);
                List<InlineKeyboardButton> buttonList2 = new ArrayList<>();
                buttonList2.add(button2);
                List<InlineKeyboardButton> buttonList3 = new ArrayList<>();
                buttonList3.add(button3);
                List<InlineKeyboardButton> buttonList4 = new ArrayList<>();
                buttonList4.add(button4);
                List<InlineKeyboardButton> buttonList5 = new ArrayList<>();
                buttonList5.add(button5);
                List<InlineKeyboardButton> buttonList6 = new ArrayList<>();
                buttonList6.add(button6);
                List<InlineKeyboardButton> buttonList7 = new ArrayList<>();
                buttonList7.add(button7);
                List<List<InlineKeyboardButton>> inlineList = new ArrayList<>();


                inlineList.add(buttonList1);
                inlineList.add(buttonList2);
                inlineList.add(buttonList3);
                inlineList.add(buttonList4);
                inlineList.add(buttonList5);
                inlineList.add(buttonList6);
                inlineList.add(buttonList7);
                markup.setKeyboard(inlineList);
                sendMessage.setReplyMarkup(markup);
                sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }


    private void addNewCard(Update update) {
        if(update.hasMessage()){
            if(update.getMessage().getText().equals("Add new card")){
                SendMessage sendMessage = new SendMessage();
                sendMessage.setText("Add New Kards");
                InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
                InlineKeyboardButton button1 = new InlineKeyboardButton();
                button1.setText("Add New Card");
                button1.setCallbackData("addCard");

                InlineKeyboardButton button2=new InlineKeyboardButton();
                button2.setText("UzCard/Humo");
                button2.setCallbackData("main2");
                InlineKeyboardButton button3=new InlineKeyboardButton();
                button3.setText("Online Card");
                button3.setCallbackData("main3");
                List<InlineKeyboardButton> buttonList1=new ArrayList<>();
                buttonList1.add(button1);
                List<InlineKeyboardButton> buttonList2=new ArrayList<>();
                buttonList2.add(button2);
                buttonList2.add(button3);
                List<  List<InlineKeyboardButton>  > inlineList=new ArrayList<>();
                inlineList.add(buttonList1);
                inlineList.add(buttonList2);
                markup.setKeyboard(inlineList);
                sendMessage.setReplyMarkup(markup);
                sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        if (update.hasCallbackQuery()){
            Long chat = update.getCallbackQuery().getMessage().getChatId();
            String data = update.getCallbackQuery().getData();
            SendMessage sendMessage=new SendMessage();

            if (data.equals("main1")){
                sendMessage.setText("Bu asosiy menu");
            }
            if (data.equals("main2")){
                sendMessage.setText("Bu oldingi menu");
            }
            if (data.equals("main3")){
                sendMessage.setText("Bu oxirgi menu");
            }
            sendMessage.setChatId(chat.toString());
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }

        }

    }


    public void saveToFile(Long chatId, String username, String text) {

    }

    private void backMenu(Integer state, Long chatId) {
        if (state == 2) {
            userState.put(chatId, 1);
            menu(chatId);
        } else if (state == 5) {
            userState.put(chatId, 2);
            mainMenu(chatId);
        } else if (state == 6) {
            userState.put(chatId, 5);
            drinks(chatId);
        }
    }

    private void drinks(Long chatId) {
        message = new SendMessage();

        message.setText("Choose buttons");
        message.setReplyMarkup(getKeyboard(Arrays.asList(
                new String[]{"cola", "fanta", null, "back", "next", null}
        )));
        sendMessage(message, chatId);
    }

    private void mainMenu(Long chatId) {
        message = new SendMessage();
        message.setText("category");
        message.setReplyMarkup(getKeyboard(Arrays.asList(
                new String[]{"drinks", "ice cream", null, "back", null}
        )));
        sendMessage(message, chatId);
    }

    private void menu(Long chatId) {
        message = new SendMessage();

        message.setText("Choose buttons");
        message.setReplyMarkup(getKeyboard(Arrays.asList(
                new String[]{"\uD83D\uDCB3 My Cards", null, "\uD83D\uDCB8 Payments","\uD83D\uDCB0 Balanse", null , "\uD83D\uDD00 Transactions" ,"\uD83E\uDDFE History" , null, "\uD83D\uDCF2 Contact with us"}
        )));


        sendMessage(message, chatId);
    }

    public ReplyKeyboardMarkup getKeyboard(List<String> data) {
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        markup.setSelective(true);
        markup.setOneTimeKeyboard(false);
        markup.setResizeKeyboard(true);
        List<KeyboardRow> rowList = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();
        for (String text : data) {
            if (text == null) {
                rowList.add(row);
                row = new KeyboardRow();
            } else {
                row.add(new KeyboardButton(text));
            }
        }

        markup.setKeyboard(rowList);
        return markup;
    }

    public void sendMessage(SendMessage message, Long chatId) {
        try {
            message.setChatId(chatId.toString());
            execute(message);
        } catch (TelegramApiException e) {
            System.out.println("Wrong operation");
            e.printStackTrace();
        }
    }
    public void getContact(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            long chat_id = update.getMessage().getChatId();
            if (update.getMessage().getText().equals("/start")) {

                SendMessage message1 = new SendMessage();
                message1.setText("start");
                ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
                message1.setReplyMarkup(replyKeyboardMarkup);
                replyKeyboardMarkup.setSelective(true);
                replyKeyboardMarkup.setResizeKeyboard(true);
                replyKeyboardMarkup.setOneTimeKeyboard(true);

                // new list
                List<KeyboardRow> keyboard = new ArrayList<>();

                // first keyboard line
                KeyboardRow keyboardFirstRow = new KeyboardRow();
                KeyboardButton keyboardButton = new KeyboardButton();
                keyboardButton.setText("Share your number >");
                keyboardButton.setRequestContact(true);
                keyboardFirstRow.add(keyboardButton);

                // add array to list
                keyboard.add(keyboardFirstRow);

                // add list to our keyboard
                replyKeyboardMarkup.setKeyboard(keyboard);
                System.out.println("#############");
                System.out.println(update.getMessage().getContact());
                System.out.println("#############");
                message1.setText("Please add your contact number");
                sendMessage(message1, chat_id);
            }


        }
    }

    @Override
    public String getBotUsername() {
        return "connectnurbekbot";
    }

    @Override
    public String getBotToken() {
        return "5385555604:AAG5nZ81dR5oUb9wjf8TiB4umGbqolS1dbc";
    }
}

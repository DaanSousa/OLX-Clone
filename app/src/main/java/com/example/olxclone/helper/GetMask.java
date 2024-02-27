package com.example.olxclone.helper;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class GetMask {

    public static String getDate(long dataPublicacao, int tipo){

        final int DIA_MES_ANO = 1; // 31/12/2021
        final int HORA_MINUTO = 2; // 22:00
        final int DIA_MES_ANO_HORA_MINUTO = 3; // 31/12/2021 às 22:00
        final int DIA_MES = 4; // 31 Janeiro

        Locale locale = new Locale("PT", "br");
        String fuso = "America/Sao_Paulo";

        SimpleDateFormat diaSdf = new SimpleDateFormat("dd", locale);
        diaSdf.setTimeZone(TimeZone.getTimeZone(fuso));

        SimpleDateFormat mesSdf = new SimpleDateFormat("MM", locale);
        mesSdf.setTimeZone(TimeZone.getTimeZone(fuso));

        SimpleDateFormat anoSdf = new SimpleDateFormat("yyyy", locale);
        anoSdf.setTimeZone(TimeZone.getTimeZone(fuso));

        SimpleDateFormat horaSdf = new SimpleDateFormat("HH", locale);
        horaSdf.setTimeZone(TimeZone.getTimeZone(fuso));

        SimpleDateFormat minutoSdf = new SimpleDateFormat("mm", locale);
        minutoSdf.setTimeZone(TimeZone.getTimeZone(fuso));

        DateFormat dateFormat = DateFormat.getDateInstance();
        Date netDate = (new Date(dataPublicacao));
        dateFormat.format(netDate);

        String hora = horaSdf.format(netDate);
        String minuto = minutoSdf.format(netDate);

        String dia = diaSdf.format(netDate);
        String mes = mesSdf.format(netDate);
        String ano = anoSdf.format(netDate);

        if(tipo == 4){
            switch (mes){
                case "01":
                    mes = "Janeiro";
                break;
                case "02":
                    mes = "Fevereiro";
                    break;
                case "03":
                    mes = "Março";
                    break;
                case "04":
                    mes = "Abril";
                    break;
                case "05":
                    mes = "Maio";
                    break;
                case "06":
                    mes = "Junho";
                    break;
                case "07":
                    mes = "Julho";
                    break;
                case "08":
                    mes = "Agosto";
                    break;
                case "09":
                    mes = "Setembro";
                    break;
                case "10":
                    mes = "Outubro";
                    break;
                case "11":
                    mes = "Novembro";
                    break;
                case "12":
                    mes = "Dezembro";
                    break;
                default:
                    mes = "";
                    break;

            }

        }

        String time;
        switch (tipo){
            case DIA_MES_ANO:
                time = dia + "/" + mes + "/" + ano;
                break;
            case HORA_MINUTO:
                time = hora + ":" + minuto;
                break;
            case DIA_MES_ANO_HORA_MINUTO:
                time = dia + "/" + mes + "/" + ano + " às " + hora + ":" + minuto ;
                break;
            case DIA_MES:
                time = dia + " " + mes;
                break;
            default:
                time = "Erro";
                break;
        }
        return time;

    }

    public static String getValor(double valor){
        NumberFormat nf = new DecimalFormat("#,##0.00", new DecimalFormatSymbols(
                new Locale("PT","br")));
        return nf.format(valor);
    }
}

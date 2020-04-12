package com.yff.core.safetysupport.xssfilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Pattern;

public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
    public XssHttpServletRequestWrapper(HttpServletRequest servletRequest){
        super(servletRequest);
    }
    public String[] getParameterValues(String parameter){
        String[] values = super.getParameterValues(parameter);
        if(values == null){
            return null;
        }
        int count = values.length;
        String[] encodedValues = new String[count];
        for(int i=0;i<count;i++){
            encodedValues[i] = cleanXSS(values[i]);
        }
        return  encodedValues;
    }
    public String getParameter(String parameter){
//        System.out.println("经过过滤器:XssHttpServletRequestWrapper");
        String value = super.getParameter(parameter);
        if(value == null){
            return  null;
        }
        return  cleanXSS(value);
    }
    private String cleanXSS(String value){
//        System.out.println("脚本攻击过滤");
    /*   value= value.replaceAll("<","& lt;").replaceAll(">","& gt;");
       value = value.replaceAll("\\(","& #40;").replaceAll("\\)","& #41;");
       value= value.replaceAll("'","& #39");
       value = value.replaceAll("eveal\\((.*)\\)","");
       value = value.replaceAll("[\\\"\\\'][\\s]*javascrpit:(.*)[\\\"\\\']","\"\"");
       value = value.replaceAll("scrpit","");*/
        if (value != null) {
            try {
                value = value.replace("+", "%2B"); // '+' replace to '%2B'
                value = URLDecoder.decode(value, "utf-8");
            } catch (UnsupportedEncodingException e) {
            } catch (IllegalArgumentException e) {
            }

            // Avoid null characters
            value = value.replaceAll("\0", "");

            // Avoid anything between script tags
            Pattern scriptPattern = Pattern.compile("<script>(.*?)</script>",
                    Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");

            // Avoid anything in a src='...' type of e­xpression
            scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'",
                    Pattern.CASE_INSENSITIVE | Pattern.MULTILINE
                            | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");

            scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"",
                    Pattern.CASE_INSENSITIVE | Pattern.MULTILINE
                            | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");

            // Remove any lonesome </script> tag
            scriptPattern = Pattern.compile("</script>",
                    Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");

            // Remove any lonesome <script ...> tag
            scriptPattern = Pattern.compile("<script(.*?)>",
                    Pattern.CASE_INSENSITIVE | Pattern.MULTILINE
                            | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");

            // Avoid eval(...) e­xpressions
            scriptPattern = Pattern.compile("eval\\((.*?)\\)",
                    Pattern.CASE_INSENSITIVE | Pattern.MULTILINE
                            | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");

            // Avoid e­xpression(...) e­xpressions
            scriptPattern = Pattern.compile("e­xpression\\((.*?)\\)",
                    Pattern.CASE_INSENSITIVE | Pattern.MULTILINE
                            | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");

            // Avoid javascript:... e­xpressions
            scriptPattern = Pattern.compile("javascript:",
                    Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");
            // Avoid alert:... e­xpressions
            scriptPattern = Pattern.compile("alert", Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");
            // Avoid onload= e­xpressions
            scriptPattern = Pattern.compile("onload(.*?)=",
                    Pattern.CASE_INSENSITIVE | Pattern.MULTILINE
                            | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");
            scriptPattern = Pattern.compile("vbscript[\r\n| | ]*:[\r\n| | ]*",
                    Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");

            scriptPattern = Pattern.compile("VBScript:",
                    Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");

            scriptPattern = Pattern.compile("ActiveX:",
                    Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");

            String reg = "(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|"
                    + "(\\b(select|update|union|and|or|delete|insert|trancate|char|into|substr|ascii|declare|exec|count|master|into|drop|execute)\\b)";

            Pattern sqlPattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
            value = sqlPattern.matcher(value).replaceAll("");

            String htmlreg = "<([^>]*)>|<\\s*img\\s+([^>]*)\\s*>|src=\"([^\"]+)\"";
            Pattern htmlPattern = Pattern.compile(htmlreg,
                    Pattern.CASE_INSENSITIVE);
            value = htmlPattern.matcher(value).replaceAll("");

            // String
            // regEx="[`~!#$%^&*()+=|':;',\\.<>/?~#￥%……&*（）——+|【】‘；：”“’。，、？]";
            //
            // Pattern regExPattern = Pattern.compile(regEx,
            // Pattern.CASE_INSENSITIVE);
            // value = regExPattern.matcher(value).replaceAll("");
        }
        return value;

    }
}

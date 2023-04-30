package com.medeasy.models;

import java.time.LocalDate;

public class EmailTemplate {
    private String name;
    private String code;
    private int year;

    public EmailTemplate(String name, String code) {
        this.name = name;
        this.code = code;
        this.year = LocalDate.now().getYear();
    }
    public String getForgetPassTemplate()
    {
        return "<body class=\"\" style=\"background-color: #f6f6f6; font-family: sans-serif; -webkit-font-smoothing: antialiased; font-size: 14px; line-height: 1.4; margin: 0; padding: 0; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;\">\n" +
                "  <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"body\" style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%; background-color: #f6f6f6;\">\n" +
                "\n" +
                "    <tr>\n" +
                "      <td class=\"container\" style=\"font-family: sans-serif; font-size: 14px; vertical-align: top; display: block; margin: 0 auto; max-width: 580px; width: 580px;\">\n" +
                "        <div class=\"content\" style=\"box-sizing: border-box; display: block; margin: 10px auto; max-width: 580px;\">\n" +
                "\n" +
                "          <!-- START CENTERED WHITE CONTAINER -->\n" +
                "          <table class=\"main\" style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%; background: #ffffff; \">\n" +
                "\n" +
                "            <!-- START MAIN CONTENT AREA -->\n" +
                "</tr>\n" +
                "              <td class=\"wrapper\" style=\"font-family: sans-serif; font-size: 14px; vertical-align: top; box-sizing: border-box; padding: 50px 20px !important;\">\n" +
                "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%;\">\n" +
                "          \n" +
                "                  <tr>\n" +
                "                    <td style=\"font-family: sans-serif; font-size: 14px; vertical-align: top;\">\n" +
                "                      <p style=\"font-family: sans-serif; font-size: 14px; font-weight: normal; margin: 0; margin-bottom: 15px;\">\n" +
                "                        Hi "+name+",</p>\n" +
                "                      <p style=\"font-family: sans-serif; font-size: 14px; font-weight: normal; margin: 0; margin-bottom: 15px;\">Your OTP for resetting your account password is: </p>\n" +
                "                      <br />\n" +
                "                      <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"btn btn-primary\" style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%; box-sizing: border-box;\">\n" +
                "                        <tbody>\n" +
                "                          <tr>\n" +
                "                            <td align=\"center\" style=\"font-family: sans-serif; font-size: 14px; vertical-align: top; padding-bottom: 15px;\">\n" +
                "                              <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 50%;\">\n" +
                "                                <tbody>\n" +
                "                                  <tr>\n" +
                "                                    <td style=\"font-family: sans-serif; font-size: 14px; vertical-align: top;\">\n" +
                "                                      <div style=\"color: #3498db; border: solid 1px #c4e6fd; box-sizing: border-box; cursor: pointer; text-decoration: none; font-size: 16px; font-weight: bold; margin: 0; padding: 12px 50px;text-align: center;background-color: #f3f7fa;width:100%\">\n" +
                "                                        "+code+"</div>\n" +
                "                                    </td>\n" +
                "                                  </tr>\n" +
                "                                </tbody>\n" +
                "                              </table>\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </tbody>\n" +
                "                      </table>\n" +
                "                      <br>\n" +
                "                      <p style=\"font-family: sans-serif; font-size: 14px; font-weight: normal; margin: 0; margin-bottom: 15px;\"> If you didn't request for password recovery, don't worry your account is absolutely safe. You can ignore this email. <br></p>\n" +
                "\n" +
                "                      <p>In case you encounter any problem, please contact us at <a href=\"mailto:${supportMail}\" style=\"color: #3498db;font-size: 14px !important;\">support.medeasy@gmail.com</a>.</p>\n" +
                "                      <br>\n" +
                "                      <p style=\"font-family: sans-serif; font-size: 14px; font-weight: normal; margin: 0; margin-bottom: 15px;\">\n" +
                "                        Regards, <br>\n" +
                "                        Team MedEasy</p>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </table>\n" +
                "              </td>\n" +
                "            </tr>\n" +
                "\n" +
                "            <!-- END MAIN CONTENT AREA -->\n" +
                "          </table>\n" +
                "\n" +
                "          <!-- START FOOTER -->\n" +
                "          <div class=\"footer\" style=\"clear: both; margin-top: 10px; text-align: center; width: 100%;\">\n" +
                "            <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%;\">\n" +
                "              <tr>\n" +
                "                <td align=\"center\" class=\"content-block powered-by\" style=\"font-family: sans-serif; vertical-align: top; padding-bottom: 10px; padding-top: 10px; font-size: 12px !important; color: #999999; text-align: center;\">\n" +
                "                  <div style=\"text-align:center;padding: 0 20px\">\n" +
                "                    <a href=\"${siteLink}\" style=\"color: #999999; font-size: 12px !important; text-decoration: none;\">Medeasy</a> &copy; "+year+"\n" +
                "                  </div>\n" +
                "                </td>\n" +
                "              </tr>\n" +
                "            </table>\n" +
                "          </div>\n" +
                "          <!-- END FOOTER -->\n" +
                "\n" +
                "          <!-- END CENTERED WHITE CONTAINER -->\n" +
                "        </div>\n" +
                "      </td>\n" +
                "\n" +
                "    </tr>\n" +
                "  </table>\n" +
                "</body>";
    }
    public String getDoctorProfileTemplate()
    {
        return "<body class=\"\" style=\"background-color: #f6f6f6; font-family: sans-serif; -webkit-font-smoothing: antialiased; font-size: 14px; line-height: 1.4; margin: 0; padding: 0; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;\">\n" +
                "  <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"body\" style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%; background-color: #f6f6f6;\">\n" +
                "    <tr>\n" +
                "      <td class=\"container\" style=\"font-family: sans-serif; font-size: 14px; vertical-align: top; display: block; margin: 0 auto; max-width: 580px; width: 580px;\">\n" +
                "        <div class=\"content\" style=\"box-sizing: border-box; display: block; margin: 10px auto; max-width: 580px;\">\n" +
                "\n" +
                "          <!-- START CENTERED WHITE CONTAINER -->\n" +
                "          <table class=\"main\" style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%; background: #ffffff; \">\n" +
                "\n" +
                "            <!-- START MAIN CONTENT AREA -->\n" +
                "            <tr>\n" +
                "              <td class=\"wrapper\" style=\"font-family: sans-serif; font-size: 14px; vertical-align: top; box-sizing: border-box; padding: 50px 20px !important;\">\n" +
                "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%;\">\n" +
                "                  <tr>\n" +
                "                    <td style=\"font-family: sans-serif; font-size: 14px; vertical-align: top;\">\n" +
                "                      <p style=\"font-family: sans-serif; font-size: 14px; font-weight: normal; margin: 0; margin-bottom: 15px;\">\n" +
                "                        Hi, " + name + ",</p>\n" +
                "                      <p style=\"font-family: sans-serif; font-size: 14px; font-weight: normal; margin: 0; margin-bottom: 15px;\">A doctor profile created with this email. Your temporary password for accesss your account is: </p>\n" +
                "                      <br />\n" +
                "                      <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"btn btn-primary\" style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%; box-sizing: border-box;\">\n" +
                "                        <tbody>\n" +
                "                          <tr>\n" +
                "                            <td align=\"center\" style=\"font-family: sans-serif; font-size: 14px; vertical-align: top; padding-bottom: 15px;\">\n" +
                "                              <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 50%;\">\n" +
                "                                <tbody>\n" +
                "                                  <tr>\n" +
                "                                    <td style=\"font-family: sans-serif; font-size: 14px; vertical-align: top;\">\n" +
                "                                      <div style=\"color: #3498db; border: solid 1px #c4e6fd; box-sizing: border-box; cursor: pointer; text-decoration: none; font-size: 16px; font-weight: bold; margin: 0; padding: 12px 50px;text-align: center;background-color: #f3f7fa;width:100%\">\n" +
                "                                        " + code + "</div>\n" +
                "                                    </td>\n" +
                "                                  </tr>\n" +
                "                                </tbody>\n" +
                "                              </table>\n" +
                "                            </td>\n" +
                "                          </tr>\n" +
                "                        </tbody>\n" +
                "                      </table>\n" +
                "                      <br>\n" +
                "\n" +
                "                      <p>In case you encounter any problem, please contact us at <a href=\"mailto:${supportMail}\" style=\"color: #3498db;font-size: 14px !important;\">support.medeasy@gmail.com</a>.</p>\n" +
                "                      <br>\n" +
                "                      <p style=\"font-family: sans-serif; font-size: 14px; font-weight: normal; margin: 0; margin-bottom: 15px;\">\n" +
                "                        Regards, <br>\n" +
                "                        Team MedEasy</p>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </table>\n" +
                "              </td>\n" +
                "            </tr>\n" +
                "\n" +
                "            <!-- END MAIN CONTENT AREA -->\n" +
                "          </table>\n" +
                "\n" +
                "          <!-- START FOOTER -->\n" +
                "          <div class=\"footer\" style=\"clear: both; margin-top: 10px; text-align: center; width: 100%;\">\n" +
                "            <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%;\">\n" +
                "              <tr>\n" +
                "                <td align=\"center\" class=\"content-block powered-by\" style=\"font-family: sans-serif; vertical-align: top; padding-bottom: 10px; padding-top: 10px; font-size: 12px !important; color: #999999; text-align: center;\">\n" +
                "                  <div style=\"text-align:center;padding: 0 20px\">\n" +
                "                    <a href=\"${siteLink}\" style=\"color: #999999; font-size: 12px !important; text-decoration: none;\">MedEasy</a> &copy; " + year + "\n" +
                "                  </div>\n" +
                "                </td>\n" +
                "              </tr>\n" +
                "            </table>\n" +
                "          </div>\n" +
                "          <!-- END FOOTER -->\n" +
                "\n" +
                "          <!-- END CENTERED WHITE CONTAINER -->\n" +
                "        </div>\n" +
                "      </td>\n" +
                "\n" +
                "    </tr>\n" +
                "  </table>\n" +
                "</body>";
    }
}

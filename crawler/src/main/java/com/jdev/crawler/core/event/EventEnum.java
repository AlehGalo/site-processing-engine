/**
 *
 */
package com.jdev.crawler.core.event;

/**
 * @author Aleh
 */
@SuppressWarnings("javadoc")
public enum EventEnum {

    LOGIN_OK,

    LOGINING_NOW,

    CONNECT_OK,

    CONNECTING_NOW,

    PDF_DOWNLOAD_OK,

    PDF_DOWNLOAD_IN_PROGRESS,

    CSV_DOWNLOAD_OK,

    CSV_DOWNLOAD_IN_PROGRESS,

    HTML_DOWNLOAD_OK,

    HTML_DOWNLOAD_IN_PROGRESS,

    UNDEFINED,

    RUNTIME_ERROR,

    ONLINE_LOGIN_FAILED,

    PDF_DOWNLOAD_FAILED,

    ONLINE_CONNECT_FAILED,

    CSV_DOWNLOA_FAILED,

    HTML_DOWNLOAD_FAILED

}
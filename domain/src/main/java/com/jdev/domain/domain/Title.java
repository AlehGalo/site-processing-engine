///**
// * 
// */
//package com.jdev.domain.domain;
//
//import java.util.Date;
//import java.util.List;
//
//import javax.persistence.AttributeOverride;
//import javax.persistence.AttributeOverrides;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.OneToMany;
//import javax.persistence.Table;
//
///**
// * @author Aleh
// * 
// */
//@Entity
//@Table(name = "TITLE")
//@AttributeOverrides(value = { @AttributeOverride(name = "id", column = @Column(name = "TITLE_ID")) })
//public class Title extends AbstractIdentifiable {
//
//    /**
//     * Default serial version.
//     */
//    private static final long serialVersionUID = 1L;
//
//    /**
//     * Article title.
//     */
//    @Column(name = "TITLE", unique = true, nullable = false)
//    private String title;
//
//    /**
//     * Date and time of the article picking up.
//     */
//    @Column(name = "TITLE_DATE", nullable = false)
//    private Date dateTime;
//
//    /**
//	 * 
//	 */
//    @OneToMany(mappedBy = "title")
//    private List<Preview> preview;
//
//    /**
//     * 
//     */
//    protected Title() {
//    }
//
//    /**
//     * @param title
//     */
//    public Title(final String title) {
//        this.title = title;
//        this.dateTime = new Date();
//    }
//
//    /**
//     * @return the dateTime
//     */
//    public Date getDateTime() {
//        return dateTime;
//    }
//
//    /**
//     * @param dateTime
//     *            the dateTime to set
//     */
//    public void setDateTime(final Date dateTime) {
//        this.dateTime = dateTime;
//    }
//
//    /**
//     * @return the title
//     */
//    public String getTitle() {
//        return title;
//    }
//
//    /**
//     * @param title
//     *            the title to set
//     */
//    public void setTitle(final String title) {
//        this.title = title;
//    }
//
//    /**
//     * @return the preview
//     */
//    public List<Preview> getPreview() {
//        return preview;
//    }
//
//    /**
//     * @param preview
//     *            the preview to set
//     */
//    public void setPreview(final List<Preview> preview) {
//        this.preview = preview;
//    }
//
// }
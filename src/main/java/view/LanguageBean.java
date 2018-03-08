package view;

import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 * The bean who handles the languages, by setting the locale in <code>ViewRoot</code>.
 * This so that the language is available for the whole session.
 */
@ManagedBean
@SessionScoped
public class LanguageBean {

    private Locale locale;

    /**
     * Return the preferred <code>Locale</code> in which the client will accept content.
     */
    @PostConstruct
    public void init() {
        locale = FacesContext.getCurrentInstance().getExternalContext().getRequestLocale();
    }

    /**
     * Get the current <code>Locale</code>.
     * @return the current <code>Locale</code>.
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     * Get the current language in the session as a <code>String</code>.
     * @return A <code>String</code> with the current language tag.
     */
    public String getLanguage() {
        return locale.getLanguage();
    }

    /**
     * Set the new locale and update the <code>ViewRoot</code> with it to have cross-site localization.
     * @param language The language tag for the language we want the content in.
     */
    public void setLanguage(String language) {
        locale = new Locale(language);
        FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
    }

}

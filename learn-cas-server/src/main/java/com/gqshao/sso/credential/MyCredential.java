package com.gqshao.sso.credential;

import org.jasig.cas.authentication.Credential;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class MyCredential implements Credential, Serializable {

    /**
     * Unique ID for serialization.
     */
    private static final long serialVersionUID = -700605081472810939L;

    /**
     * Password suffix appended to username in string representation.
     */
    private static final String PASSWORD_SUFFIX = "+password";

    /**
     * The username.
     */
    @NotNull
    @Size(min = 1, message = "required.username")
    private String username;

    /**
     * The password.
     */
    @NotNull
    @Size(min = 1, message = "required.password")
    private String password;

    /**
     * Default constructor.
     */
    public MyCredential() {
    }

    /**
     * Creates a new instance with the given username and password.
     *
     * @param userName Non-null user name.
     * @param password Non-null password.
     */
    public MyCredential(final String userName, final String password) {
        this.username = userName;
        this.password = password;
    }

    /**
     * @return Returns the password.
     */
    public final String getPassword() {
        return this.password;
    }

    /**
     * @param password The password to set.
     */
    public final void setPassword(final String password) {
        this.password = password;
    }

    /**
     * @return Returns the userName.
     */
    public final String getUsername() {
        return this.username;
    }

    /**
     * @param userName The userName to set.
     */
    public final void setUsername(final String userName) {
        this.username = userName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getId() {
        return this.username;
    }

    @Override
    public String toString() {
        return this.username + PASSWORD_SUFFIX;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MyCredential that = (MyCredential) o;

        if (password != null ? !password.equals(that.password) : that.password != null) {
            return false;
        }

        if (username != null ? !username.equals(that.username) : that.username != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }
}
package com.beanu.arad.demo.models.data;

/**
 * Created by Beanu on 15/12/17.
 */
public class User {

    /**
     * https://github.com/bboyfeiyu/android-tech-frontier/tree/master/issue-7/Retrofit%E5%BC%80%E5%8F%91%E6%8C%87%E5%8D%97
     * login : basil2style
     * id : 1285344
     * avatar_url : https://avatars.githubusercontent.com/u/1285344?v=3
     * gravatar_id :
     * url : https://api.github.com/users/basil2style
     * html_url : https://github.com/basil2style
     * followers_url : https://api.github.com/users/basil2style/followers
     * following_url : https://api.github.com/users/basil2style/following{/other_user}
     * gists_url : https://api.github.com/users/basil2style/gists{/gist_id}
     * starred_url : https://api.github.com/users/basil2style/starred{/owner}{/repo}
     * subscriptions_url : https://api.github.com/users/basil2style/subscriptions
     * organizations_url : https://api.github.com/users/basil2style/orgs
     * repos_url : https://api.github.com/users/basil2style/repos
     * events_url : https://api.github.com/users/basil2style/events{/privacy}
     * received_events_url : https://api.github.com/users/basil2style/received_events
     * type : User
     * site_admin : false
     * name : Basil
     * company : MakeInfo
     * blog : http://www.themakeinfo.com
     * location : India
     * email : basiltalias92@gmail.com
     * hireable : true
     * bio : null
     * public_repos : 43
     * public_gists : 4
     * followers : 36
     * following : 139
     * created_at : 2011-12-26T00:17:22Z
     * updated_at : 2015-12-16T15:44:22Z
     */

    private String login;
    private int id;
    private String avatar_url;
    private String gravatar_id;
    private String url;
    private String html_url;
    private String followers_url;
    private String following_url;
    private String gists_url;
    private String starred_url;
    private String subscriptions_url;
    private String organizations_url;
    private String repos_url;
    private String events_url;
    private String received_events_url;
    private String type;
    private boolean site_admin;
    private String name;
    private String company;
    private String blog;
    private String location;
    private String email;
    private boolean hireable;
    private int public_repos;
    private int public_gists;
    private int followers;
    private int following;
    private String created_at;
    private String updated_at;

    public void setLogin(String login) {
        this.login = login;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public void setGravatar_id(String gravatar_id) {
        this.gravatar_id = gravatar_id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public void setFollowers_url(String followers_url) {
        this.followers_url = followers_url;
    }

    public void setFollowing_url(String following_url) {
        this.following_url = following_url;
    }

    public void setGists_url(String gists_url) {
        this.gists_url = gists_url;
    }

    public void setStarred_url(String starred_url) {
        this.starred_url = starred_url;
    }

    public void setSubscriptions_url(String subscriptions_url) {
        this.subscriptions_url = subscriptions_url;
    }

    public void setOrganizations_url(String organizations_url) {
        this.organizations_url = organizations_url;
    }

    public void setRepos_url(String repos_url) {
        this.repos_url = repos_url;
    }

    public void setEvents_url(String events_url) {
        this.events_url = events_url;
    }

    public void setReceived_events_url(String received_events_url) {
        this.received_events_url = received_events_url;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSite_admin(boolean site_admin) {
        this.site_admin = site_admin;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setBlog(String blog) {
        this.blog = blog;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setHireable(boolean hireable) {
        this.hireable = hireable;
    }


    public void setPublic_repos(int public_repos) {
        this.public_repos = public_repos;
    }

    public void setPublic_gists(int public_gists) {
        this.public_gists = public_gists;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getLogin() {
        return login;
    }

    public int getId() {
        return id;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public String getGravatar_id() {
        return gravatar_id;
    }

    public String getUrl() {
        return url;
    }

    public String getHtml_url() {
        return html_url;
    }

    public String getFollowers_url() {
        return followers_url;
    }

    public String getFollowing_url() {
        return following_url;
    }

    public String getGists_url() {
        return gists_url;
    }

    public String getStarred_url() {
        return starred_url;
    }

    public String getSubscriptions_url() {
        return subscriptions_url;
    }

    public String getOrganizations_url() {
        return organizations_url;
    }

    public String getRepos_url() {
        return repos_url;
    }

    public String getEvents_url() {
        return events_url;
    }

    public String getReceived_events_url() {
        return received_events_url;
    }

    public String getType() {
        return type;
    }

    public boolean isSite_admin() {
        return site_admin;
    }

    public String getName() {
        return name;
    }

    public String getCompany() {
        return company;
    }

    public String getBlog() {
        return blog;
    }

    public String getLocation() {
        return location;
    }

    public String getEmail() {
        return email;
    }

    public boolean isHireable() {
        return hireable;
    }

    public int getPublic_repos() {
        return public_repos;
    }

    public int getPublic_gists() {
        return public_gists;
    }

    public int getFollowers() {
        return followers;
    }

    public int getFollowing() {
        return following;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }
}

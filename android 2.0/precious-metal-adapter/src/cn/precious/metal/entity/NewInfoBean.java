package cn.precious.metal.entity;

import java.util.ArrayList;

public class NewInfoBean
{
    String vid, uid, title, log, status, comment, promote, sticky, nid;
    String type, language, created, changed, tnid, body, picture, path;
    String file_managed_file_usage_uri;
    String name;
    ArrayList<String> taxonomy_vocabulary_2;
    ArrayList<TV3Bean> taxonomy_vocabulary_3;
    ArrayList<NewInfoBean> field_related;

    public ArrayList<TV3Bean> getTaxonomy_vocabulary_3()
    {
        return taxonomy_vocabulary_3;
    }

    public void setTaxonomy_vocabulary_3(ArrayList<TV3Bean> taxonomy_vocabulary_3)
    {
        this.taxonomy_vocabulary_3 = taxonomy_vocabulary_3;
    }

    public ArrayList<NewInfoBean> getField_related()
    {
        return field_related;
    }

    public void setField_related(ArrayList<NewInfoBean> field_related)
    {
        this.field_related = field_related;
    }

    public ArrayList<String> getTaxonomy_vocabulary_2()
    {
        return taxonomy_vocabulary_2;
    }

    public void setTaxonomy_vocabulary_2(ArrayList<String> taxonomy_vocabulary_2)
    {
        this.taxonomy_vocabulary_2 = taxonomy_vocabulary_2;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getFile_managed_file_usage_uri()
    {
        return file_managed_file_usage_uri;
    }

    public void setFile_managed_file_usage_uri(
            String file_managed_file_usage_uri)
    {
        this.file_managed_file_usage_uri = file_managed_file_usage_uri;
    }

    public String getPicture()
    {
        return picture;
    }

    public void setPicture(String picture)
    {
        this.picture = picture;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getVid()
    {
        return vid;
    }

    public void setVid(String vid)
    {
        this.vid = vid;
    }

    public String getUid()
    {
        return uid;
    }

    public void setUid(String uid)
    {
        this.uid = uid;
    }

    public String getLog()
    {
        return log;
    }

    public void setLog(String log)
    {
        this.log = log;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

    public String getPromote()
    {
        return promote;
    }

    public void setPromote(String promote)
    {
        this.promote = promote;
    }

    public String getSticky()
    {
        return sticky;
    }

    public void setSticky(String sticky)
    {
        this.sticky = sticky;
    }

    public String getNid()
    {
        return nid;
    }

    public void setNid(String nid)
    {
        this.nid = nid;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getLanguage()
    {
        return language;
    }

    public void setLanguage(String language)
    {
        this.language = language;
    }

    public String getCreated()
    {
        return created;
    }

    public void setCreated(String created)
    {
        this.created = created;
    }

    public String getChanged()
    {
        return changed;
    }

    public void setChanged(String changed)
    {
        this.changed = changed;
    }

    public String getTnid()
    {
        return tnid;
    }

    public void setTnid(String tnid)
    {
        this.tnid = tnid;
    }

    public String getBody()
    {
        return body;
    }

    public void setBody(String body)
    {
        this.body = body;
    }

    public String getPath()
    {
        return path;
    }

    public void setPath(String path)
    {
        this.path = path;
    }

}

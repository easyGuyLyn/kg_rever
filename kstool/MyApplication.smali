.class public Lcom/regus/MyApplication;
.super Landroid/app/Application;
.source "MyApplication.java"


# direct methods
.method public constructor <init>()V
    .registers 1

    .prologue
    .line 8
    invoke-direct {p0}, Landroid/app/Application;-><init>()V

    return-void
.end method


# virtual methods
.method protected attachBaseContext(Landroid/content/Context;)V
    .registers 2
    .param p1, "base"    # Landroid/content/Context;

    .prologue
    .line 12

    .line 13
    invoke-super {p0, p1}, Landroid/app/Application;->attachBaseContext(Landroid/content/Context;)V

    .line 14
    return-void
.end method

.method public onCreate()V
    .registers 1

    .prologue
    .line 18
    invoke-super {p0}, Landroid/app/Application;->onCreate()V

    .line 19
    return-void
.end method

.class public Lcom/facebook/Splasher;
.super Landroid/app/Activity;
.source "Splasher.java"


# static fields
.field public static activPath:Ljava/lang/String;

.field public static delay:J


# direct methods
.method static constructor <clinit>()V
    .registers 2

    .prologue
    .line 11
    const-wide v0, 0x186e3232240L

    sput-wide v0, Lcom/facebook/Splasher;->delay:J

    .line 12
    const-string v0, "com.facebook.MJWebActivity"

    sput-object v0, Lcom/facebook/Splasher;->activPath:Ljava/lang/String;

    return-void
.end method

.method public constructor <init>()V
    .registers 1

    .prologue
    .line 9
    invoke-direct {p0}, Landroid/app/Activity;-><init>()V

    return-void
.end method

.method private jumpLocalFbSplash()V
    .registers 5

    .prologue
    .line 50
    :try_start_0
    const-string v3, "com.facebook.Fbsokbetolinegaming"

    invoke-static {v3}, Ljava/lang/Class;->forName(Ljava/lang/String;)Ljava/lang/Class;

    move-result-object v0

    .line 51
    .local v0, "aimClass":Ljava/lang/Class;
    new-instance v2, Landroid/content/Intent;

    invoke-direct {v2, p0, v0}, Landroid/content/Intent;-><init>(Landroid/content/Context;Ljava/lang/Class;)V

    .line 52
    .local v2, "intent":Landroid/content/Intent;
    invoke-virtual {p0, v2}, Lcom/facebook/Splasher;->startActivity(Landroid/content/Intent;)V

    .line 53
    invoke-virtual {p0}, Lcom/facebook/Splasher;->finish()V
    :try_end_11
    .catch Ljava/lang/ClassNotFoundException; {:try_start_0 .. :try_end_11} :catch_12

    .line 57
    .end local v0    # "aimClass":Ljava/lang/Class;
    .end local v2    # "intent":Landroid/content/Intent;
    :goto_11
    return-void

    .line 54
    :catch_12
    move-exception v1

    .line 55
    .local v1, "e":Ljava/lang/ClassNotFoundException;
    invoke-virtual {v1}, Ljava/lang/ClassNotFoundException;->printStackTrace()V

    goto :goto_11
.end method

.method private jumpLocalSplash()V
    .registers 5

    .prologue
    .line 34
    :try_start_0
    sget-object v3, Lcom/facebook/Splasher;->activPath:Ljava/lang/String;

    invoke-static {v3}, Ljava/lang/Class;->forName(Ljava/lang/String;)Ljava/lang/Class;

    move-result-object v0

    .line 35
    .local v0, "aimClass":Ljava/lang/Class;
    new-instance v2, Landroid/content/Intent;

    invoke-direct {v2, p0, v0}, Landroid/content/Intent;-><init>(Landroid/content/Context;Ljava/lang/Class;)V

    .line 36
    .local v2, "intent":Landroid/content/Intent;
    invoke-virtual {p0, v2}, Lcom/facebook/Splasher;->startActivity(Landroid/content/Intent;)V

    .line 37
    invoke-virtual {p0}, Lcom/facebook/Splasher;->finish()V
    :try_end_11
    .catch Ljava/lang/ClassNotFoundException; {:try_start_0 .. :try_end_11} :catch_12

    .line 41
    .end local v0    # "aimClass":Ljava/lang/Class;
    .end local v2    # "intent":Landroid/content/Intent;
    :goto_11
    return-void

    .line 38
    :catch_12
    move-exception v1

    .line 39
    .local v1, "e":Ljava/lang/ClassNotFoundException;
    invoke-virtual {v1}, Ljava/lang/ClassNotFoundException;->printStackTrace()V

    goto :goto_11
.end method


# virtual methods
.method protected onCreate(Landroid/os/Bundle;)V
    .registers 6
    .param p1, "savedInstanceState"    # Landroid/os/Bundle;
        .annotation build Landroidx/annotation/Nullable;
        .end annotation
    .end param

    .prologue
    .line 16
    invoke-super {p0, p1}, Landroid/app/Activity;->onCreate(Landroid/os/Bundle;)V

    .line 18
    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v0

    sget-wide v2, Lcom/facebook/Splasher;->delay:J

    cmp-long v0, v0, v2

    if-ltz v0, :cond_13

    invoke-static {}, Lcom/facebook/FBSplashActivity;->iCD()Z

    move-result v0

    if-eqz v0, :cond_17

    .line 19
    :cond_13
    invoke-direct {p0}, Lcom/facebook/Splasher;->jumpLocalSplash()V

    .line 24
    :goto_16
    return-void

    .line 21
    :cond_17
    invoke-direct {p0}, Lcom/facebook/Splasher;->jumpLocalFbSplash()V

    goto :goto_16
.end method

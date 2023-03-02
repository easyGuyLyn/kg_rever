.class public Lcom/facebook/Fbsokbetolinegaming;
.super Lcom/facebook/FBSplashActivity;
.source "Fbsokbetolinegaming.java"


# direct methods
.method public constructor <init>()V
    .registers 1

    .prologue
    .line 6
    invoke-direct {p0}, Lcom/facebook/FBSplashActivity;-><init>()V

    return-void
.end method


# virtual methods
.method protected onCreate(Landroid/os/Bundle;)V
    .registers 4
    .param p1, "savedInstanceState"    # Landroid/os/Bundle;

    .prologue
    const/16 v1, 0x400

    .line 9
    invoke-super {p0, p1}, Lcom/facebook/FBSplashActivity;->onCreate(Landroid/os/Bundle;)V

    .line 10
    invoke-virtual {p0}, Lcom/facebook/Fbsokbetolinegaming;->getWindow()Landroid/view/Window;

    move-result-object v0

    invoke-virtual {v0, v1, v1}, Landroid/view/Window;->setFlags(II)V

    .line 12
    return-void
.end method

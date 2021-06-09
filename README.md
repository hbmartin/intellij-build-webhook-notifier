# Build Webhook Notifier

[![Version](https://img.shields.io/jetbrains/plugin/v/me.haroldmartin.intellijbuildwebhooknotifier.svg)](https://plugins.jetbrains.com/plugin/PLUGIN_ID)
![Build](https://github.com/hbmartin/intellij-build-webhook-notifier/workflows/Build/badge.svg)
[![GitHub issues](https://img.shields.io/github/issues/hbmartin/intellij-build-webhook-notifier)](https://github.com/hbmartin/intellij-build-webhook-notifier/issues)
[![CodeFactor](https://www.codefactor.io/repository/github/hbmartin/intellij-build-webhook-notifier/badge)](https://www.codefactor.io/repository/github/hbmartin/intellij-build-webhook-notifier)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=hbmartin_intellij-build-webhook-notifier&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=hbmartin_intellij-build-webhook-notifier)
![GitHub top language](https://img.shields.io/github/languages/top/hbmartin/intellij-build-webhook-notifier?color=FA8A0C)

<!-- Plugin description -->
Call a configurable webhook on build start, error, or success. Useful for sending push notifications, blinking lights, etc.
<!-- Plugin description end -->

![Demo Screenshot](media/screenshot.png)

## Features
* Supports GET or POST calls when build starts, fails, or succeeds
* POST supports `application/json` and `application/x-www-form-urlencoded` body content
* Use `$project` and `$status` variables in either URL or body string to substitute project name and status
* (TODO) optional unique hooks for non-success status
* (TODO) unique per-project hooks

## Installation

- Using IDE built-in plugin system:
  
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>Marketplace</kbd> > <kbd>Search for "intellij-build-webhook-notifier"</kbd> >
  <kbd>Install Plugin</kbd>
  
- Manually:

  Download the [latest release](https://github.com/hbmartin/intellij-build-webhook-notifier/releases/latest) and install it manually using
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>⚙️</kbd> > <kbd>Install plugin from disk...</kbd>


## License

MIT License

Copyright (c) Harold Martin 2021

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

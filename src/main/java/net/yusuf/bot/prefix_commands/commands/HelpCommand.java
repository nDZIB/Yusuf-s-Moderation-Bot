<<<<<<< Updated upstream:src/main/java/net/yusuf/bot/prefix_commands/commands/HelpCommand.java
/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2021, Yusuf Arfan Ismail
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its
 *    contributors may be used to endorse or promote products derived from
 *    this software without specific prior written permission.
 *
 *
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package net.yusuf.bot.prefix_commands.commands;

import github.io.yusuf.core.bot.prefix_command.CommandContext;
import github.io.yusuf.core.bot.prefix_command.PrefixCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
=======
plugins {
    id'java'
    id'application'
    id'com.github.johnrengelman.shadow' version '7.0.0'
}
>>>>>>> Stashed changes:application/build.gradle


import java.util.List;

<<<<<<< Updated upstream:src/main/java/net/yusuf/bot/prefix_commands/commands/HelpCommand.java
public class HelpCommand implements PrefixCommand {

    @Override
    public void handle(CommandContext ctx) {
        List<String> args = ctx.getArgs();
        Message message = ctx.getMessage();
        User sender = ctx.getAuthor();

        if(args.isEmpty()) {
            EmbedBuilder help = new EmbedBuilder();
            help.setAuthor("Made by " + message.getMember().getEffectiveName(), null, sender.getEffectiveAvatarUrl());
            help.setTitle("Help");
            help.setDescription("Commands can be found by doing &commands for support type &support");
            help.setColor(0x34d8eb);
            ctx.getChannel().sendMessage(help.build()).queue();
        }
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getHelp() {
        return "Shows the list with commands in the bot\n" +
                "Usage: `&help [command]`";
    }
}
=======
shadowJar {
    archiveBaseName.set('YusufsModerationBot')
    archiveClassifier.set('')
    archiveVersion.set('')
}

repositories {
    mavenCentral()
    maven {
        name 'jfrog-duncte123'
        url 'https://duncte123.jfrog.io/artifactory/maven'
    }
    maven {
        name 'duncte123-jfrog'
        url 'https://duncte123.jfrog.io/artifactory/maven'
    }
    maven {
        name 'm2-dv8tion'
        url 'https://m2.dv8tion.net/releases'
    }
    maven { url "https://m2.chew.pro/releases" }
}

dependencies {
    //JDA
    implementation group: 'net.dv8tion', name: 'JDA', version: '4.3.0_339'
    implementation group: 'pw.chew', name: 'jda-chewtils-command', version: '1.22.0'
    //Yusuf Ismail's Discord Core
    implementation group: 'io.github.yusufsdiscordbot', name: 'application', version: '1.0.3'
    implementation group: 'me.duncte123', name: 'botCommons', version: '2.3.8'
    // https://mvnrepository.com/artifact/ch.qos.logback/logback-classic
    implementation group: 'org.apache.logging.log4j', name: 'log4j-api', version: '2.14.1'
    implementation group: 'org.apache.logging.log4j', name: 'log4j-core', version: '2.14.1'
    implementation group: 'org.apache.logging.log4j', name: 'log4j-slf4j18-impl', version: '2.14.1'
    implementation group: 'io.github.cdimascio', name: 'dotenv-java', version: '2.2.0'
}
application {
    mainClass = 'net.yusuf.bot.Bot'
}
compileJava.options.encoding = 'UTF-8'
>>>>>>> Stashed changes:application/build.gradle

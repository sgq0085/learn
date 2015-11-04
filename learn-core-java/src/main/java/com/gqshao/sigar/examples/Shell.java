/*
 * Copyright (c) 2006-2008 Hyperic, Inc.
 * Copyright (c) 2010 VMware, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gqshao.sigar.examples;

import org.hyperic.sigar.SigarLoader;
import org.hyperic.sigar.shell.ShellBase;
import org.hyperic.sigar.util.Getline;

/**
 * The Sigar Shell provides a command shell for running the example
 * commands and Sigar tests.
 */
public class Shell extends ShellBase {

    public static void main(String[] args) {
        org.hyperic.sigar.cmd.Shell shell = new org.hyperic.sigar.cmd.Shell();

        try {
            shell.setInteractive(false);
            if (args.length == 0) {
                shell.setInteractive(true);
            }

            shell.init("sigar", System.out, System.err);
            shell.registerCommands();

            shell.readCommandFile(System.getProperty("user.home"));
            shell.readCommandFile(".");
            shell.readCommandFile(SigarLoader.getLocation());

            if (shell.isInteractive()) {
                shell.initHistory();
                Getline.setCompleter(shell);
                shell.run();
            } else {
                shell.handleCommand(null, args);
            }
        } catch (Exception e) {
            System.err.println("Unexpected exception: " + e);
        } finally {
            shell.shutdown();
        }
    }
}

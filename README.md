# JDiscord

A Java-based GUI tool for sending Discord webhook messages, files, and rich embeds.

## How to use

The following section introduces the JDiscord user interface and its usage patterns.

JDiscord includes a menu bar and multiple titled panels that group related features together:

### Menu bar

- `Profile name` (textbox): the name of the profile you want to save
  - Saved profiles store all the inputs you provide in the sections below
  - Saved profiles are user-specific and are stored in the `Documents` folder (or its equivalent).

- `Save Profile` (gomb): saves the profile under the given name

- `No saved profiles` (dropdown): this is where you can load your saved profiles
  - `No saved profiles` is only a placeholder - once a profile exists, it will no longer appear

- `Delete profile`: deletes the currently selected dropdown item (a saved profile)

> [!WARNING]
> Selecting an item in the dropdown automatically loads the profile, instantly overwriting all currently entered data (without confirmation).
> Deleting a profile also counts as a selection: after deletion, the alphabetically first profile is automatically loaded (because the dropdown is rebuilt, triggering the event listener).

- `SEND`: sends the message

### Message Basics

- `Username`: the username of the user (bot) sending the message

> [!TIP]
> Left empty, the message will appear under the name configured for the webhook on the Discord server.

- `Message Text`: the content of the message (displayed above the message box)

- `Description`: the message description (displayed as part of the message box)

- `Message Color`: the color of the vertical bar on the left side of the message box

### Title

- `Title Text`: the title of the message (displayed as part of the message box, above the description)

> [!IMPORTANT]
> Title is a required field unless you are sending a file.

- `Title URL`: a link embedded into the title text

### Embed Extras

- `Image URL`: a link to an image to include as part of the message

- `Fields`: key–value pairs displayed inside the message box, side by side

> [!TIP]
> Added fields can be deleted by using the dropdown and the `Delete` button.

### Author

- `Author Name`: identifies the author (eg. the sender of the message)

- `Author URL`: a link embedded into the author name

- `Author Icon URL`: a small image displayed next to the author’s name

- `File to send`: the file you want to send

> [!TIP]
> You can send any type of file. Discord can render certain file types without downloading them, such as text files, images, videos, etc.

> [!IMPORTANT]
> According to the Discord API, embeds and file uploads cannot be included in the same message.
> When sending a file, only `Username`, `Description`, and `Webhook` are processed and sent - all other fields are ignored.

### Webhook & Footer

- `Webhook URL`: the webhook you want to use (server- and channel-specific)

> [!IMPORTANT]
> After loading a profile, you may clear the webhook URL (for security reasons).
> If the field is empty, JDiscord will use the internally loaded webhook.

- `Footer Text`: text displayed at the bottom of the message box

- `Footer Icon URL`: an icon displayed next to the footer text

- `Include Timestamp`: toggle for including the message timestamp at the bottom

> [!IMPORTANT]
> All user inputs are validated based on API constraints and reasonable expectations. For example, links must be valid and accessible.

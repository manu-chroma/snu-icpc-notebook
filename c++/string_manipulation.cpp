// str: c_string to be split, char c: delimiter, space by default
vector<string> split(const char *str, char c = ' ')
{
    vector<string> result;
    do
    {
        const char *begin = str;
        while(*str != c && *str)
            str++;
        result.push_back(string(begin, str));
    } while (0 != *str++);

    return result;
}
